import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PensionDetail } from '../models/pension-detail';
import { PensionerInput } from '../models/pensioner-input';


@Injectable({
  providedIn: 'root'
})
export class ProcessPensionService {

  // add your base URL here
  //baseUrl: string = 'http://localhost:8082/api/process-pension'
  baseUrl: string = 'http://service-lb-2012331825.us-east-1.elb.amazonaws.com/api/process-pension'
  constructor(private http: HttpClient) { }


  // Method to get pension details
  getPensionDetails(pensionerInput: PensionerInput): Observable<PensionDetail> {
    return this.http.post<PensionDetail>(`${this.baseUrl}/pensionerInput`, pensionerInput);
  }

}
