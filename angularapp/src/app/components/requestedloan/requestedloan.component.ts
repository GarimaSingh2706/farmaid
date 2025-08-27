import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoanApplication } from 'src/app/models/loanapplication.model';
import { LoanService } from 'src/app/services/loan.service';

@Component({
  selector: 'app-requestedloan',
  templateUrl: './requestedloan.component.html',
  styleUrls: ['./requestedloan.component.css']
})
export class RequestedloanComponent implements OnInit {
  loanApplications: LoanApplication[] = [];
  filteredApplications: LoanApplication[] = [];
  filterForm: FormGroup;
  selectedApplication?: LoanApplication;
  showModal: boolean = false;

  // Pagination
  currentPage = 1;
  pageSize = 5;

  constructor(private fb: FormBuilder, private loanService: LoanService) {}

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      searchText: [''],
      status: ['0']
    });
    this.loadData();
  }

  loadData() {
    this.filterForm.valueChanges.subscribe(() => this.applyFilters());
    this.loanService.getAllLoanApplication().subscribe(data => {
      this.loanApplications = data;
      this.loanApplications.forEach(app => {
        if (app.file && !app.file.startsWith('data:image')) {
          app.file = 'data:image/jpeg;base64,' + app.file;
        }
      });
      this.applyFilters();
    });
  }

  applyFilters() {
    const { searchText, status } = this.filterForm.value;
    this.filteredApplications = this.loanApplications.filter(app => {
      const matchesSearch = app.farmPurpose.toLowerCase().includes(searchText?.toLowerCase() || '');
      const matchesStatus = status === '' || app.loanStatus.toString() === status;
      return matchesSearch && matchesStatus;
    });
    this.currentPage = 1; // Reset to first page on filter change
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

  approve(app: LoanApplication) {
    app.loanStatus = 1;
    this.loanService.updateLoanApplicationStatus(app.loanApplicationId!, app.loanStatus).subscribe(() => {
      this.loadData();
    });
  }

  reject(app: LoanApplication) {
    app.loanStatus = 2;
    this.loanService.updateLoanApplicationStatus(app.loanApplicationId!, app.loanStatus).subscribe(() => {
      this.loadData();
    });
  }

  showDetails(app: LoanApplication) {
    this.selectedApplication = app;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  getStatusLabel(status: number): string {
    switch (status) {
      case 0: return 'Pending';
      case 1: return 'Approved';
      case 2: return 'Rejected';
      case 3: return 'Cancelled';
      default: return 'Unknown';
    }
  }
}
