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

  uploadFile(platformSelect2: string, fileInput: any) {
    console.log('File input:', fileInput);
    console.log('File input files:', fileInput.files);
    
    const file = fileInput.files[0];
    const platform = platformSelect2;

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

    let apiUrl: string;
    if (platform === 'liferay') {
      apiUrl = 'http://localhost:8080/xmlEater/convertAemXml';
      console.log('Called http://localhost:8080/xmlEater/convertLiferayXml')
    } else if (platform === 'aem') {
      apiUrl = 'http://localhost:8080/xmlEater/convertAemXml';
      console.log('Called http://localhost:8080/xmlEater/convertAemXml')
    } else {
      console.error('Invalid platform:', platform);
      return; // Exit function if an invalid platform is selected
    }

    this.apiService.convertXml(formData,apiUrl).subscribe(response=>
      console.log("API call successful"));

  }
}
