import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, ErrorHandler } from '@angular/core';


import { SimpleNotificationsModule } from 'angular2-notifications';

import { AppComponent } from './components/app.component';
import { MasterComponent } from './components/master.component';
import { PageNotFoundComponent } from './components/page.not.found.component';
import { UtilModule } from '../util/util.module';
import { SidebarComponent } from './components/sidebar.component';
import { HomeComponent } from './components/home.component';

@NgModule({
  declarations: [
    AppComponent
    ,  MasterComponent, PageNotFoundComponent, 
     SidebarComponent, HomeComponent
  ],
  imports: [
     BrowserAnimationsModule, 
    SimpleNotificationsModule.forRoot()
    , UtilModule
  ],
  exports: [AppComponent]
})
export class CommonCoreModule {}

