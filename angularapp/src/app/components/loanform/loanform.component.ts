import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoanApplication } from 'src/app/models/loanapplication.model';
import { LoanService } from 'src/app/services/loan.service';
import { LocationService } from 'src/app/services/location.service';

@Component({
  selector: 'app-loanform',
  templateUrl: './loanform.component.html',
  styleUrls: ['./loanform.component.css']
})
export class LoanformComponent implements OnInit {
  loanForm: FormGroup;
  submitted = false;
  showPopup = false;
  userId: number;
  loanId: number;
  fileData: string = '';
  previewUrl: string = '';
  states: string[] = [];
  districts: string[] = [];

  constructor(
    private fb: FormBuilder,
    private loanService: LoanService,
    private locationService: LocationService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.userId = +sessionStorage.getItem('userId');
    this.loanId = +this.route.snapshot.paramMap.get('loanId');

    this.loanForm = this.fb.group({
      state: ['', Validators.required],
      district: ['', Validators.required],
      farmLocation: ['', Validators.required],
      farmerAddress: ['', Validators.required],
      farmSizeInAcres: [null, [Validators.required, Validators.min(1)]],
      farmPurpose: ['', Validators.required],
      file: ['', Validators.required]
    });
    this.loadStatesAndDistricts();
  }

  loadStatesAndDistricts() {
    this.locationService.getStatesAndDistricts().subscribe(data => {
      console.log('States API response:', data);
      this.states = data.map((item: any) => item.state);
    });
  }
  showEligibilityModal = false;
  showEligibilityInfo() {
    this.showEligibilityModal = true;
  }

  closeEligibilityInfo() {
    this.showEligibilityModal = false;
  }
  onStateChange(state: string) {
    this.loanForm.patchValue({ district: '' }); // reset district
    this.locationService.getDistrictsByState(state).subscribe(data => {
      this.districts = data;
    });
  }
  onFileChange(event: any) {
    const file = event.target.files[0];
    if (!file) return;
    if (!file.type.startsWith('image/')) {
      this.loanForm.patchValue({ file: '' });
      this.fileData = '';
      this.previewUrl = '';
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      this.fileData = reader.result as string;
      this.previewUrl = this.fileData;
      this.loanForm.patchValue({ file: this.fileData })
      this.loanForm.get('file')?.markAsDirty();
      this.loanForm.get('file')?.updateValueAndValidity()
    };
    reader.readAsDataURL(file);
  }

  evaluateLoanEligibility(formData: any): { eligible: boolean; category: string; loanAmount: number; message: string } {
    const { farmLocation, farmSizeInAcres, farmPurpose } = formData;

    let loanAmount = 0;
    let category = '';

    // Farm Size Criteria
    if (farmSizeInAcres < 2) {
      loanAmount = 50000;
      category = 'Small Farm';
    } else if (farmSizeInAcres >= 2 && farmSizeInAcres <= 10) {
      loanAmount = 150000;
      category = 'Medium Farm';``
    } else {
      loanAmount = 300000;
      category = 'Large Farm';
    }

    // Purpose Criteria
    if (farmPurpose.toLowerCase().includes('organic')) {
      loanAmount += 25000;
    }

    // Location-Based Bonus
    const priorityLocations = ['Karnataka', 'Punjab', 'Maharashtra'];
    if (priorityLocations.some(loc => farmLocation.toLowerCase().includes(loc.toLowerCase()))) {
      loanAmount += 10000;
    }

    return {
      eligible: true,
      category,
      loanAmount,
      message: `Eligible for ₹${loanAmount} loan under ${category} category.`
    };
  }
  successMessage = '';
  onSubmit(): void {
    this.submitted=true
    const allFieldsEmpty = Object.values(this.loanForm.value).every(value => value === '' || value === null);
    if (allFieldsEmpty) {
    this.successMessage = 'All fields must be filled';
    this.showPopup = false;
    return;
    }
    if (this.loanForm.invalid) {
      return;
    }
    const eligibility = this.evaluateLoanEligibility(this.loanForm.value);
    this.successMessage = eligibility.message;
    this.showPopup = true;
    const application: LoanApplication = {
      ...this.loanForm.value,
      userId: this.userId,
      loanId: this.loanId,
      file: this.fileData
    };

    this.loanService.addLoanApplication(application, this.userId, this.loanId).subscribe(() => {
      this.showPopup = true;
    });
    console.log(this.loanForm.value)
  }
  goBack() {
    this.router.navigate(['/view-loans']);
  }
  closePopup() {
    this.showPopup = false;
    this.router.navigate(['/applied-loan']);
  }
}
