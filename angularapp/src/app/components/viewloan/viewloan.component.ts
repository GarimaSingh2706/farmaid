import { Component, OnInit } from '@angular/core';
import { Loan } from 'src/app/models/loan.model';
import { LoanService } from 'src/app/services/loan.service';
import { Router } from '@angular/router';
declare var bootstrap: any;

@Component({
  selector: 'app-viewloan',
  templateUrl: './viewloan.component.html',
  styleUrls: ['./viewloan.component.css']
})
export class ViewloanComponent implements OnInit {
  loans: Loan[] = [];
  filteredLoans: Loan[] = [];

  searchKeyword: string = '';
  selectedAttribute: string = 'loanType';
  sortOrder: string = 'asc';

  showSuccessModal = false;
  loanToDeleteId: number | null = null;
  showDeleteModal = false;

  // Pagination
  currentPage: number = 1;
  pageSize: number = 5;

  constructor(private loanService: LoanService, private router: Router) {}

  ngOnInit(): void {
    this.fetchLoans();
  }

  fetchLoans(): void {
    this.loanService.getAllLoans().subscribe({
      next: (data: Loan[]) => {
        this.loans = data;
        this.applyFilterAndSort();
      },
      error: (err) => {
        console.error('Error fetching loans:', err);
      }
    });
  }

  applyFilterAndSort(): void {
    this.filteredLoans = this.loans
      .filter(loan =>
        String(loan[this.selectedAttribute as keyof Loan] ?? '')
          .toLowerCase()
          .includes(this.searchKeyword.toLowerCase())
      )
      .sort((a, b) => {
        const valueA = String(a[this.selectedAttribute as keyof Loan]).toLowerCase();
        const valueB = String(b[this.selectedAttribute as keyof Loan]).toLowerCase();
        return this.sortOrder === 'asc' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
      });

    this.currentPage = 1;
  }

  get paginatedLoans(): Loan[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.filteredLoans.slice(startIndex, startIndex + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredLoans.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  onDelete(loanId: number): void {
    this.loanToDeleteId = loanId;
    const modalElement = document.getElementById('deleteModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  confirmDelete(): void {
    if (this.loanToDeleteId !== null) {
      this.loanService.loanActivateDeactive(this.loanToDeleteId, 0).subscribe(() => {
        this.fetchLoans();
        this.loanToDeleteId = null;
        this.showSuccessModal = true;

        const modalElement = document.getElementById('deleteModal');
        if (modalElement) {
          const modalInstance = bootstrap.Modal.getInstance(modalElement);
          if (modalInstance) {
            modalInstance.hide();
          }
        }
      });
    }
  }

  closeSuccessModal(): void {
    this.showSuccessModal = false;
  }

  reactivateLoan(loanId: number): void {
    this.loanService.loanActivateDeactive(loanId, 1).subscribe(() => {
      this.fetchLoans();
    });
  }

  onEdit(id: number): void {
    this.router.navigate(['/edit-loan', id]);
  }
}
