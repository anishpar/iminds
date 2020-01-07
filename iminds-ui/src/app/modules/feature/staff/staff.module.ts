import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';


import { WorkListComponent } from './components/work-list.component';



const routes: Routes = [
  { path: 'mywork', component: WorkListComponent }

];

@NgModule({
  imports: [
    RouterModule.forChild(routes), SharedModule
  ],
  declarations: [WorkListComponent],
  providers: [],
  exports: []
})


export class StaffModule { }

