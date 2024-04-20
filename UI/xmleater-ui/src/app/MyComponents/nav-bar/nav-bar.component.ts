import { Component } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {
  wordpressXml: string = '';
  convertedXml: string = '';
  
  convertXml() {
    // Here you can implement the logic to convert WordPress XML to AEM XML
    // Update this.convertedXml with the result
    console.log('Converting XML...');
  }
}
