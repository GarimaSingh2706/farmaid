import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Feedback } from '../models/feedback.model';
import { API_URL } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  constructor(private client: HttpClient) { }

  sendFeedback(feedback: Feedback, userId: string): Observable<Feedback> {
    return this.client.post<Feedback>(`${API_URL}/feedback/user/${userId}`, feedback)
  }

  getAllFeedbacksByUser(userId: number): Observable<Feedback[]> {
    return this.client.get<Feedback[]>(`${API_URL}/feedback/user/${userId}`)
  }

  deleteFeedback(feedbackId: number): Observable<void> {
    return this.client.delete<void>(`${API_URL}/feedback/${feedbackId}`)
  }

  getFeedbacks(): Observable<Feedback[]> {
    return this.client.get<Feedback[]>(`${API_URL}/feedback`)
  }
}