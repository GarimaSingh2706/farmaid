import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { AuthService } from 'src/app/services/auth.service';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-userviewfeedback',
  templateUrl: './userviewfeedback.component.html',
  styleUrls: ['./userviewfeedback.component.css']
})
export class UserviewfeedbackComponent implements OnInit {
  view: Feedback[];
  showDeletePopup = false;
  selectedFeedbackId?: number;
  userId = +sessionStorage.getItem('userId'); //fall back
  emojiMap: { [key: number]: string } = {
    1: '😠',
    2: '😐',
    3: '🙂',
    4: '😍'
  };

  constructor(private service:FeedbackService, private userService:AuthService) { }

  ngOnInit(): void {
    this.loadData(this.userId);
  }
  loadData(userId) {
    this.service.getAllFeedbacksByUser(userId).subscribe((result) => {
      this.view = result;
    },
      (error) => {
        console.log('User not found');
      })
  }

  openDeletePopup(id: number) {
    this.selectedFeedbackId = id;
    this.showDeletePopup = true;
  }

  closeDeletePopup() {
    this.showDeletePopup = false;
    this.selectedFeedbackId = null;
  }

  confirmDelete() {
    this.service.deleteFeedback(this.selectedFeedbackId).subscribe((result) => {
      this.loadData(this.userId);
    })
    this.closeDeletePopup();
  }

}
