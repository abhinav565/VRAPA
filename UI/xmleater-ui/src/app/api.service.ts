import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }
  uploadFile(platform:string, file: File){
    const formData=new FormData();
    formData.append('targetFormat', platform);
    formData.append('file', file);
    return this.http.post<any>('http://localhost:8080/xmlEater/convertAemXml', formData)
  }
}
