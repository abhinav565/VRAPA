import { Component } from '@angular/core';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.css'
})
export class FileUploadComponent {

  uploadFile(platform:string, fileInput:any){
    const file=fileInput.files[0];
    console.log('Platform: ', platform)
    console.log('File uploaded: ', file)
  }
}
