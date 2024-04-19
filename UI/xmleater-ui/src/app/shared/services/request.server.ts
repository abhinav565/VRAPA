import { HttpClient, HttpHeaders } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { Observable } from "rxjs"

@Injectable({
    providedIn:'root',
})
export class requestServer{
    basePath:string='http://localhost:8080/xmlEater'
    public headers=new HttpHeaders({'Content-Type':'application/json'})
    public fileHeaders=new HttpHeaders({'Content-Type':'x-www-form-urlencoded'})

    constructor(public http:HttpClient){}

    get(req:any):Observable<any>{
        return this.http.get(this.basePath+req.uri,{headers:this.headers,params:req.params})
    }

    post(req:any):Observable<any>{
        return this.http.post(this.basePath+req.uri,req.data,{headers:req.headers ? req.headers:this.headers,params:req.params})
    }

    postFile(req:any):Observable<any>{
        return this.http.post(this.basePath+req.uri,req.data,{headers:this.headers,params:req.params,observe:'response',responseType:'blob'})
    }
}