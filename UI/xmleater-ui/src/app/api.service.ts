import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  convertAemXml(formData: FormData) {
    console.log("API Called");
    return this.http.post('http://localhost:8080/xmlEater/convertAemXml', formData, { responseType: 'text' });
    
  }
}
