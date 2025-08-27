import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';
import { Login } from '../models/login.model';
import { API_URL } from '../app.constant';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private roleSubject = new BehaviorSubject<string | null>(null);
  private userIdSubject = new BehaviorSubject<string | null>(null);

  constructor(private client: HttpClient, private router: Router) { }

  register(user: User): Observable<any> {
    return this.client.post(`${API_URL}/register`, user);
  }

  login(login: Login): Observable<any> {
    return this.client.post(`${API_URL}/login`, login).pipe(
      tap(response => {
        if (response && response.token) {
          sessionStorage.setItem('token', response.token);
          sessionStorage.setItem('userRole', response.role); // Optional: for isAdmin/isUser
          this.roleSubject.next(response.role);
          this.userIdSubject.next(response.userId?.toString());
        }
      })
    );
  }

  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userRole');
    this.roleSubject.next(null);
    this.userIdSubject.next(null);
    this.router.navigate(['/home']);
  }

  isAdmin(): boolean {
    return sessionStorage.getItem('userRole') === 'ADMIN';
  }

  isUser(): boolean {
    return sessionStorage.getItem('userRole') === 'USER';
  }

  getRole(): string {
    return sessionStorage.getItem('userRole') || '';
  }


  getRoleObservable(): Observable<string | null> {
    return this.roleSubject.asObservable();
  }

  getUserIdObservable(): Observable<string | null> {
    return this.userIdSubject.asObservable();
  }

  goToHome(): void {
    this.router.navigate(['/home']);
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('token');
  }

}
