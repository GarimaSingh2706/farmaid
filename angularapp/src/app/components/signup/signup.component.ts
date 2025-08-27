import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  regForm: FormGroup;
  showPassword: boolean = false;
  showConfirmPassword: boolean = false;

  popupMessage: string | null = null;
  popupType: 'success' | 'error' = 'success';

  passwordCriteria = {
    uppercase: false,
    lowercase: false,
    number: false,
    special: false,
    length: false
  };

  constructor(private fb: FormBuilder, private router: Router, public service: AuthService) {}

  checkPasswordMatch(fg: FormGroup): ValidationErrors | null {
    if (fg.controls.password.value !== fg.controls.confirmPassword.value) {
      fg.get('confirmPassword')?.setErrors({ 'mismatch': true });
    }
    return null;
  }

  validatePassword(): void {
    const value = this.regForm.controls.password.value || '';
    this.passwordCriteria.uppercase = /[A-Z]/.test(value);
    this.passwordCriteria.lowercase = /[a-z]/.test(value);
    this.passwordCriteria.number = /[0-9]/.test(value);
    this.passwordCriteria.special = /[^A-Za-z0-9]/.test(value);
    this.passwordCriteria.length = value.length >= 8;
  }

  onRegister(): void {
    if (this.regForm.valid) {
      this.service.register({
        email: this.regForm.value.email,
        username: this.regForm.value.username,
        mobileNumber: this.regForm.value.mobileNumber,
        password: this.regForm.value.password,
        userRole: 'USER'
      } as User).subscribe({
        next: () => {
          this.popupType = 'success';
          this.popupMessage = 'User Registered Successfully!';
          setTimeout(() => {
            this.popupMessage = null;
            this.router.navigate(['/login']);
          }, 3000);
        },
        error: () => {
          this.popupType = 'error';
          this.popupMessage = 'Registration Failed. Please try again.';
          setTimeout(() => this.popupMessage = null, 3000);
        }
      });
    }
  }
  ngOnInit(): void {
    this.regForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      username: ['', [Validators.required]],
      mobileNumber: ['', [Validators.pattern('^[0-9]{10}$'), Validators.required]],
      password: ['', [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}')]],
      confirmPassword: ['', [Validators.required]]
    }, { validators: [this.checkPasswordMatch] });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
}
