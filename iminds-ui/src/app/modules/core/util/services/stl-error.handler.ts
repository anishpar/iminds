import { ErrorHandler, Injectable } from '@angular/core';
import { CrudeService } from './crude.service';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StlErrorHandler implements ErrorHandler {

  constructor(public _service: CrudeService) {}


  public handleError(err: any) {
    let message = '';
    if (err) {
      if (err.message) message = err.message;
      if (err.err_msg) message = err.err_msg;
    }
    const errorData = {
        'uiLog': message,
    };
    if (!environment.production) {
      console.error(err);
    }
    this._service.fetchPost('LOG_ERROR', errorData).subscribe((res) => {
        console.log('error reported successfully');
    }, err => {
      console.log('error while loggin error');
    });
  }
}
