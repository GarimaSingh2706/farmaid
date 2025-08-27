import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit {

  constructor() { }

  funFacts = [
    "🌾 Did you know? AI can detect crop diseases before they spread!",
    "🛰️ Satellites help us monitor soil moisture in real-time.",
    "🌦️ Hyperlocal weather helps farmers plan better harvests."
  ];
  
  ngOnInit() {
    let index = 0;
    setInterval(() => {
      document.getElementById('funFact')!.innerText = this.funFacts[index];
      index = (index + 1) % this.funFacts.length;
    }, 4000);
  }
  

}
