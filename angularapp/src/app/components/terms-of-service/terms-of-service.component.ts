import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-terms-of-service',
  templateUrl: './terms-of-service.component.html',
  styleUrls: ['./terms-of-service.component.css']
})
export class TermsOfServiceComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  privacyPoints = [
    { title: 'No Selling Data', content: 'We never sell your personal data.', open: false },
    { title: 'No Creepy Tracking', content: 'We only track what’s necessary to help you.', open: false },
    { title: 'Full Transparency', content: 'You can view and delete your data anytime.', open: false }
  ];
  
}
