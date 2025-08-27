import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FeedbackService } from 'src/app/services/feedback.service';


@Component({
  selector: 'app-useraddfeedback',
  templateUrl: './useraddfeedback.component.html',
  styleUrls: ['./useraddfeedback.component.css']
})
export class UseraddfeedbackComponent implements OnInit {
  feedbackForm:FormGroup;
  showPopup=false;
  errorPopup=false;
  userId=sessionStorage.getItem('userId');
  emojiMap: { [key: number]: string } = {
    1: '😠',
    2: '😐',
    3: '🙂',
    4: '😍'
  };
  popupMessage = '';

  emojis = Object.entries(this.emojiMap); 

  constructor(private service:FeedbackService, private fb:FormBuilder, private router: Router) {}

  ngOnInit(): void {  
    this.feedbackForm=this.fb.group({
      feedbackText:['',[Validators.required,Validators.minLength(4)]],
      rating: ['']  
    })
  }
  
  addFeedback(){
    const forbiddenWords = ['bad', 'hell', 'worst', 'waste'];
    const feedbackText = this.feedbackForm.get('feedbackText')?.value.toLowerCase();

    const containsForbidden = forbiddenWords.some(word => feedbackText.includes(word));

    if (containsForbidden) {
      this.popupMessage = '❌ Your feedback contains inappropriate words. Please revise.';
      this.errorPopup = true;
      return;
    }

    if(this.feedbackForm.valid){
      this.service.sendFeedback(this.feedbackForm.value, this.userId).subscribe((result)=>{
        console.log('SUCCESSFUL', result)
        this.showPopup=true;
        this.feedbackForm.reset();
      })
    }
  }
  closePopup(){
    this.showPopup=false;
    this.router.navigate(['/my-feedbacks']);
  }

  closeErrorPopup(){
    this.errorPopup=false;
    this.router.navigate(['/post-feedback']);
  }

}
