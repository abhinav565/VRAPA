import { Component } from '@angular/core';
import { ApiService } from '../../api.service';
import { response } from 'express';
@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.css'
})
export class FileUploadComponent {
  uploadMessage: string = '';
  constructor(private apiService: ApiService) { }

  uploadFile(platform: string, fileInput: any) {
    console.log('File input:', fileInput);
    console.log('File input files:', fileInput.files);
    
    const file = fileInput.files[0];

    if (!file) {
      this.uploadMessage = 'Please select a file.';
      return; // Exit function if no file is selected
    } else if (file.type !== 'text/xml') {
      this.uploadMessage = 'Please select an XML file.';
      return; // Exit function if the selected file is not an XML file
    } else {
      this.uploadMessage = ''; // Clear the message if a valid XML file is selected
    }


    const formData = new FormData();
    formData.append('file', file);
    formData.append('targetFormat', platform);

    this.apiService.convertAemXml(formData).subscribe(response=>console.log("API call successful"));

  }
}
