import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { LoanApplication } from 'src/app/models/loanapplication.model';
import { LoanService } from 'src/app/services/loan.service';

@Component({
  selector: 'app-userappliedloan',
  templateUrl: './userappliedloan.component.html',
  styleUrls: ['./userappliedloan.component.css']
})
export class UserappliedloanComponent implements OnInit {
  applications: LoanApplication[] = [];
  searchControl = new FormControl('');
  selectedApp?: LoanApplication;
  showModal = false;

  selectedAppToCancel?: LoanApplication;
  showCancelModal: boolean = false;

  // Pagination
  currentPage = 1;
  pageSize = 5;

  constructor(private loanService: LoanService) {}

  ngOnInit(): void {
    const userId = +sessionStorage.getItem('userId');
    this.loadApplications(userId);
  }

  loadApplications(userId: number): void {
    this.loanService.getAppliedLoans(userId).subscribe({
      next: (data) => {
        this.applications = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  get filteredApplications(): LoanApplication[] {
    const searchText = this.searchControl.value?.toLowerCase() || '';
    return this.applications.filter(app =>
      app.farmPurpose?.toLowerCase().includes(searchText)
    );
  }

  get paginatedApplications(): LoanApplication[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.filteredApplications.slice(startIndex, startIndex + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredApplications.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  showDetails(app: LoanApplication): void {
    this.selectedApp = app;
    this.showModal = true;
  }

  openCancelModal(app: LoanApplication): void {
    if (app.loanStatus !== 0) {
      return;
    }
    this.selectedAppToCancel = app;
    this.showCancelModal = true;
  }

  confirmCancel(): void {
    if (!this.selectedAppToCancel) return;

    const applicationId = this.selectedAppToCancel.loanApplicationId;
    const userId = Number(sessionStorage.getItem('userId'));

    this.loanService.updateLoanApplicationStatus(applicationId, 3).subscribe({
      next: () => {
        this.loadApplications(userId);
        this.showCancelModal = false;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }
}
