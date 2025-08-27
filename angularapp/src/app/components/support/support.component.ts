import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  submitted = false;

  submitForm() {
    this.submitted = true;
    setTimeout(() => this.submitted = false, 3000);
  }


}
