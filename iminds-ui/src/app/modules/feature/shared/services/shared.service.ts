import { Injectable } from '@angular/core';
import { CrudeService } from '../../../core/util/services/crude.service';

@Injectable({
  'providedIn': 'root'
})
export class SharedService {

  constructor(private _service: CrudeService) { }
  

  getOrderHistory(data: any) {
    return this._service.fetchPost('GET_HISTORY', data);
  }

}
