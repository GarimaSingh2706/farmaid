import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../app.constant';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private client: HttpClient) { }

  getStatesAndDistricts(): Observable<any> {
    return this.client.get(`${API_URL}/location/states`);
  }

  getDistrictsByState(state: string): Observable<string[]> {
    return this.client.get<string[]>(`${API_URL}/location/districts?state=${state}`);
  }
  
}
