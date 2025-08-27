import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  services = [
    { icon: '🌾', title: 'Crop Monitoring', desc: 'AI watches over your crops like a hawk.' },
    { icon: '🌦️', title: 'Weather Forecasting', desc: 'Hyperlocal, real-time, and farmer-friendly.' },
    { icon: '🧪', title: 'Soil Analysis', desc: 'Know your soil like never before.' },
    { icon: '📈', title: 'Yield Prediction', desc: 'Plan your harvest with confidence.' },
    { icon: '🛰️', title: 'Satellite Insights', desc: 'Bird’s-eye view of your farm.' }
  ];
  
}
