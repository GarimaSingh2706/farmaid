import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  isAdmin = false;
  isUser = false;
  constructor(private router: Router, private authService: AuthService) { }
  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin()
    this.isUser = this.authService.isUser()
    if (this.isAdmin || this.isUser) {
      this.isLoggedIn = true
    }
    console.log(this.isAdmin)
    console.log(this.isUser)
  }
  logout() {
    this.authService.logout();
  }
}




