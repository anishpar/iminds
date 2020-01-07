import { Injectable } from '@angular/core';
import { CrudeService } from '../../../core/util/services/crude.service';

@Injectable({providedIn: 'root'})
export class StaffService {
  constructor(public _service: CrudeService) {
  }
    
  getSampleData() {
    return this._service.fetch('CHANNEL_MASTER');
  }

  createSampleData(data, id :string) {
    return this._service.post('SAMPLE_API_CONSTANT', data , id);
  }
}
