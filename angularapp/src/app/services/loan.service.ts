import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Loan } from '../models/loan.model';
import { Observable } from 'rxjs';
import { LoanApplication } from '../models/loanapplication.model';
import { API_URL } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  constructor(private client:HttpClient) { 
  }
  
  getAllLoans():Observable<Loan[]>{
    return this.client.get<Loan[]>(`${API_URL}/loan`)
  }


  loanActivateDeactive(loanId:number,status:number):Observable<any>{
    return this.client.put(`${API_URL}/loan/activate/${loanId}/${status}`,{})
  }

  getLoanById(id:number):Observable<Loan>{
    return this.client.get<Loan>(`${API_URL}/loan/${id}`)
  }

  addLoan(requestObject: Loan): Observable<Loan> {
    return this.client.post<Loan>(`${API_URL}/loan`, requestObject)
  }

  updateLoan(id: number, requestObject: Loan): Observable<Loan> {
    return this.client.put<Loan>(`${API_URL}/loan/${id}`, requestObject)
  }

  getAppliedLoans(userId: number): Observable<LoanApplication[]> {
    return this.client.get<LoanApplication[]>(`${API_URL}/loanapplication/user/${userId}`)
  }

  deleteLoanApplication(loanId: number): Observable<void> {
    return this.client.delete<void>(`${API_URL}/loanapplication/${loanId}`)
  }

  addLoanApplication(data: LoanApplication, userId: any, loanId: any): Observable<any> {
    console.log(data)
    return this.client.post(`${API_URL}/loanapplication/${userId}/${loanId}`, data)
  }

  getAllLoanApplication(): Observable<LoanApplication[]> {
    return this.client.get<LoanApplication[]>(`${API_URL}/loanapplication`)
  }

  updateLoanStatus(id: number, loanApplication: LoanApplication): Observable<LoanApplication> {
    return this.client.put<LoanApplication>(`${API_URL}/loanapplication/${id}`, loanApplication)
  }
  updateLoanApplicationStatus(loanId: number, newStatus: number) {
    return this.client.put(`${API_URL}/loanapplication?loanApplicationId=${loanId}&status=${newStatus}`, { status: newStatus })
  }
}
