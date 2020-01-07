import { NgModule  } from '@angular/core';

import { UtilModule } from '../util/util.module';
import { LoginComponent } from './components/login.component';

@NgModule({
  declarations: [ LoginComponent],
  imports: [ UtilModule ],
  exports: [LoginComponent]
})
export class LoginModule { }
