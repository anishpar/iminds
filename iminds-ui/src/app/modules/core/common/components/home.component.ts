import { Component, OnInit } from '@angular/core';
import { MasterComponent } from './master.component';

@Component({
  selector: 'stl-home',
  templateUrl: './home.component.html'
})
export class HomeComponent extends MasterComponent implements OnInit {

  constructor() { 
    super();
  }

  ngOnInit() {
  }

}
