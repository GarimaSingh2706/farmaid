import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  showPassword: boolean = false;
  isSuccessPopupVisible = false;
  isErrorPopupVisible = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private service: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}')
      ]]
    });
  }

  onLogin(): void {
    if (this.loginForm.valid) {
      const credentials = {
        email: this.loginForm.value.email,
        password: this.loginForm.value.password
      };
      this.service.login(credentials).subscribe({
        next: (result) => {
          console.log(result);
          sessionStorage.setItem('token', result.token);
          sessionStorage.setItem('email', result.email);
          sessionStorage.setItem('userId', result.id);
          sessionStorage.setItem('userRole', result.role); 

          this.isSuccessPopupVisible = true;
          console.log('user id:' ,result.id)
          setTimeout(() => {
            this.isSuccessPopupVisible = false;
            this.router.navigate(['/home']);
          }, 2000);
        },
        error: (err) => {
          this.errorMessage = err?.error?.message || 'Login failed. Please try again.';
          this.isErrorPopupVisible = true;
          setTimeout(() => {
            this.isErrorPopupVisible = false;
          }, 2500);
        }
      });
    }
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
}
