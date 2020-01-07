import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DataStoreService {
  private _data = {};
  constructor() { }

  public setData(key: string, val: any) {
    this._data[key] = val;
  }
  public getData(key: string) {
    return this._data[key];
  }

  public hasData(key: string): boolean {
    return !(typeof this._data[key] === 'undefined');
  }

  public unsetData(key: string): boolean {
    if (this.hasData(key)) {
      delete this._data[key];
      return true;
    }
    return false;
  }

}
