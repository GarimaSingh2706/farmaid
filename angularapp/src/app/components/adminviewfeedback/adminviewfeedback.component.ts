import { Component, OnInit } from '@angular/core';
import { Feedback } from 'src/app/models/feedback.model';
import { FeedbackService } from 'src/app/services/feedback.service';

@Component({
  selector: 'app-adminviewfeedback',
  templateUrl: './adminviewfeedback.component.html',
  styleUrls: ['./adminviewfeedback.component.css']
})
export class AdminviewfeedbackComponent implements OnInit {
  view:Feedback[] =[];
  filteredView: Feedback[]=[];
  searchUserID: string = '';
  emojiMap: { [key: number]: string } = {
    1: '😠',
    2: '😐',
    3: '🙂',
    4: '😍'
  };
  constructor(private service:FeedbackService) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.service.getFeedbacks().subscribe((result) => {
      this.view = result;
      this.applySearch();
    })
  }

  applySearch(): void {
    let term = this.searchUserID;
    if (!term) {
      this.filteredView = this.view;
      return;
    }
    this.filteredView = this.view.filter(fb => fb.userId.toString() === term);
  }

  clearSearch(): void {
    this.searchUserID = '';
    this.applySearch();
  }
}
