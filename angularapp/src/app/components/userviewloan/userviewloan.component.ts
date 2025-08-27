import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Loan } from 'src/app/models/loan.model';
import { LoanService } from 'src/app/services/loan.service';

@Component({
  selector: 'app-userviewloan',
  templateUrl: './userviewloan.component.html',
  styleUrls: ['./userviewloan.component.css']
})
export class UserviewloanComponent implements OnInit {
  loans: Loan[] = [];
  filteredLoans: Loan[] = [];

  searchKeyword: string = '';
  selectedAttribute: string = 'loanType';
  sortOrder: string = 'asc';

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
    const filtered = this.loans
      .filter(loan =>
        String(loan[this.selectedAttribute as keyof Loan])
          .toLowerCase()
          .includes(this.searchKeyword.toLowerCase())
      )
      .sort((a, b) => {
        const valueA = String(a[this.selectedAttribute as keyof Loan]).toLowerCase();
        const valueB = String(b[this.selectedAttribute as keyof Loan]).toLowerCase();
        return this.sortOrder === 'asc' ? valueA.localeCompare(valueB) : valueB.localeCompare(valueA);
      });

    this.filteredLoans = filtered;
    this.currentPage = 1; // Reset to first page on new search
  }

  get paginatedLoans(): Loan[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredLoans.slice(start, start + this.pageSize);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredLoans.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  onApply(loanId: number): void {
    this.router.navigate(['/apply-loan', loanId]);
  }

  // EMI Calculator
  principal: number = 0;
  interestRate: number = 0;
  tenure: number = 0;
  emiResult: number | null = null;
  totalPayable: number | null = null;
  showCalculator: boolean = false;

  calculateEMI(): void {
    const P = this.principal;
    const R = this.interestRate / (12 * 100); // Monthly interest rate
    const N = this.tenure;

    if (P > 0 && R > 0 && N > 0) {
      const emi = (P * R * Math.pow(1 + R, N)) / (Math.pow(1 + R, N) - 1);
      this.emiResult = parseFloat(emi.toFixed(2));
      this.totalPayable = parseFloat((emi * N).toFixed(2));
    } else {
      this.emiResult = null;
      this.totalPayable = null;
    }
  }

  toggleCalculator(): void {
    this.showCalculator = !this.showCalculator;
  }

  closeCalculator(){
    this.showCalculator = false;
  }
}
