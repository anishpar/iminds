import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie';

@Injectable({
  providedIn: 'root'
})
export class StlCookieService {

  constructor(private _cookie: CookieService) { }

  get(key: string) {
    return this._cookie.get(key);
  }

  remove(key: string) {
    return this._cookie.remove(key);
  }

  removeAll() {
    return this._cookie.removeAll();
  }

  put(key: string, val: any) {
    return this._cookie.put(key, val);
  }

}
