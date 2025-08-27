import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {


  isLoggedIn = false;
  isAdmin = false;
  isUser = false;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.isUser = this.authService.isUser();
    this.isLoggedIn = this.isAdmin || this.isUser;
  }
  navigateToExplore(): void {
    if (this.isAdmin) {
      this.router.navigate(['/view-loan']);
    } else if (this.isUser) {
      this.router.navigate(['/view-loans']);
    } else {
      // Optional: redirect to login or show error
      this.router.navigate(['/login']);
    }
  }

}
