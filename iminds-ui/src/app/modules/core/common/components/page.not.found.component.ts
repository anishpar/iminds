import { Component } from '@angular/core';
import { MasterComponent } from './master.component';

@Component({
  selector: 'stl-404',
  template: ' <h3 class="page-header">{{lang.pageNotFoundMsg}}</h3>'
})
export class PageNotFoundComponent extends MasterComponent { }
