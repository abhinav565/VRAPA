import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  convertXml(formData: FormData, apiUrl: string) {
    console.log("API Called");
    return this.http.post(apiUrl, formData, { responseType: 'text' });
    
  }
}
