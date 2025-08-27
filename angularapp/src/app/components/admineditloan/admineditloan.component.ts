import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Loan } from 'src/app/models/loan.model';
import { LoanService } from 'src/app/services/loan.service';

declare var bootstrap: any;
@Component({
  selector: 'app-admineditloan',
  templateUrl: './admineditloan.component.html',
  styleUrls: ['./admineditloan.component.css']
})
export class AdmineditloanComponent implements OnInit {
  editLoanForm!: FormGroup;
  loan!: Loan;
  submitted = false;
  id!: number;
  showSuccessModal = false; 
  months: number[] = [6, 12, 18, 24, 36, 48, 60];

  constructor(
    private fb: FormBuilder,
    private loanService: LoanService,
    private router: Router,
    private arouter: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const idParam = this.arouter.snapshot.paramMap.get('id');
    console.log(idParam)
    if (idParam) {
      this.id = +idParam;

      this.loanService.getLoanById(this.id).subscribe((loanData) => {
        this.loan = loanData;
        this.initializeForm();
      });
    } else {
      this.router.navigate(['/view-loan']); // fallback if no ID
    }
  }

  initializeForm(): void {
    this.editLoanForm = this.fb.group({
      loanType: [this.loan.loanType, Validators.required],
      description: [this.loan.description, Validators.required],
      interestRate: [
        this.loan.interestRate,
        [Validators.required, Validators.pattern(/^(?!0\d)\d{1,2}(\.\d{1,2})?$/)]
      ],
      maximumAmount: [
        this.loan.maximumAmount,
        [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]
      ],
      repaymentTenure: [this.loan.repaymentTenure, Validators.required],
      eligibility: [this.loan.eligibility, Validators.required],
      documentsRequired: [this.loan.documentsRequired, Validators.required]
    });
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.editLoanForm.valid) {
      this.loanService.updateLoan(this.id, this.editLoanForm.value).subscribe(() => {
        const modalElement = document.getElementById('successModal');
        if (modalElement) {
          const modal = new bootstrap.Modal(modalElement);
          modal.show();
        }
        this.editLoanForm.reset();
        this.submitted = false;
      });
    } else {
      console.log('Form is invalid:', this.editLoanForm.errors);
    }
  }

  closeModal(): void {
    this.router.navigate(['/view-loan']);
  }
}
