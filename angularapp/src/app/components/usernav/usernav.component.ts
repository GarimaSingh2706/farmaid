import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
declare var bootstrap: any; 
@Component({
  selector: 'app-usernav',
  templateUrl: './usernav.component.html',
  styleUrls: ['./usernav.component.css']
})
export class UsernavComponent implements OnInit {
  isLoggingOut = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  showLogoutModal() {
    const modalElement = document.getElementById('logoutModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }
  logout() {
    this.isLoggingOut = true;
    this.authService.logout();

    const modalElement = document.getElementById('logoutModal');
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    if (modalInstance) {
      modalInstance.hide();
    }

    setTimeout(() => {
      this.isLoggingOut = false;
      this.router.navigate(['/login']);
    }, 1000); // Simulate delay for UX
  }
}
