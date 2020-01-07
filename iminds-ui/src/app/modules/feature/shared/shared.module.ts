import { NgModule } from '@angular/core';
import { UtilModule } from '../../core/util/util.module';
import { AllHistoryComponent } from './components/all-history.component';

@NgModule({
  declarations: [ AllHistoryComponent],
  imports: [
    UtilModule
  ],
  providers: [],

  exports: [
      AllHistoryComponent, UtilModule
    ]
})
export class SharedModule { }
