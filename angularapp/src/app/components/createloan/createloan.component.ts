import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoanService } from 'src/app/services/loan.service';

declare var bootstrap: any;

@Component({
  selector: 'app-createloan',
  templateUrl: './createloan.component.html',
  styleUrls: ['./createloan.component.css']
})
export class CreateloanComponent implements OnInit {

  loanForm!: FormGroup;
  submitted = false;
  months: number[] = [6, 12, 18, 24, 36, 48, 60];

  constructor(private fb: FormBuilder, private loanService: LoanService, private router: Router) {}

  ngOnInit(): void {
    this.loanForm = this.fb.group({
      loanType: ['', Validators.required],
      description: ['', Validators.required],
      interestRate: ['', [
        Validators.required,
        Validators.pattern(/^(?!0\d)\d{1,2}(\.\d{1,2})?$/)
      ]],
      maximumAmount: ['', [
        Validators.required,
        Validators.pattern(/^\d+(\.\d{1,2})?$/) 
      ]],
      repaymentTenure: ['', Validators.required],
      eligibility: ['', Validators.required],
      documentsRequired: ['', Validators.required]
    });
  }
  

  onSubmit(): void {
    this.submitted = true;
    if (this.loanForm.valid) {
      this.loanService.addLoan(this.loanForm.value).subscribe(() => {
        const modalElement = document.getElementById('successModal');
        if (modalElement) {
          const modal = new bootstrap.Modal(modalElement);
          modal.show();
        }
        this.loanForm.reset();
        this.submitted = false;
      });
    } else {
      console.log('Form is invalid:', this.loanForm.errors);
    }
  }

  closeModal(): void {
    this.router.navigate(['/view-loan']);
  }
}

