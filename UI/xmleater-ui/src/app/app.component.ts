import { Component, OnInit } from '@angular/core';
import { CommonService } from './shared/services/common.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  constructor(private service:CommonService){}
  msg=""
  ngOnInit(): void {
   this.service.getTest().subscribe(res=>{
    console.log(res)
    this.msg=res
   })
  }
  
  title = 'xmleater-ui';
}
