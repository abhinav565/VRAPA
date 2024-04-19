import { Injectable } from '@angular/core';
import { requestServer } from './request.server';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private server:requestServer) { }
   getTest(){
    let request={
      uri:'/test'
    }
    return this.server.get(request)
   }
}
