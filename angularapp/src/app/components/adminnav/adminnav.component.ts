import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
declare var bootstrap: any;
@Component({
  selector: 'app-adminnav',
  templateUrl: './adminnav.component.html',
  styleUrls: ['./adminnav.component.css']
})
export class AdminnavComponent implements OnInit {

  isLoggingOut = false;
  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

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
    }, 1000);
  }
}
