import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs';
import { NotificationsService } from 'angular2-notifications';
import { map, catchError } from 'rxjs/operators';

import { APIConfigurations } from '../../../../config/api.config';
import { API_MODE } from '../../../../config/config';
import { commonMessage } from '../../../../language/common.message';
import { StlCookieService } from './stl-cookie.service';
import { StartupService } from './startup.service';

@Injectable({
  providedIn: 'root'
})
export class CrudeService {
  private apiLists = {};
  public errorMessage;

  public message;
  private unAuthorizedCode = 111900800;
  public API_HOST_JSON;
  public _activeCalls = 0;

  constructor(private http: HttpClient
    , private notify: NotificationsService
    , private _cookie: StlCookieService
    , private startup: StartupService
  ) {
    this.activeCalls = 0;
  }


  getHeaderOption(secure = true): any {
    let httpOptions;
    if (secure) {
      const token = this._cookie.get('authToken') || '';
      httpOptions = {
        headers: new HttpHeaders({ 'authorization': token })
      };
    } else {
      httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      };
    }
    return httpOptions;
  }

  fetch(api_key, extraParam = '', show_success_notify = false, secure = true) {

    let api_url = this.getAPIUrl(api_key);
    if (!api_url) {
      return throwError('Requested API not found:' + api_key);
    }

    if (extraParam !== '' && API_MODE.toString() !== 'DEV') {
      api_url = api_url + extraParam;
    }

    this.showLoader();

    let httpOptions;
        if (secure) {
          const token = this._cookie.get('authToken') || '';
          httpOptions = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json', 'authorization': token })
          };
         
        }
    
    
    return this.http.get(api_url, httpOptions).pipe(map((res: any) => {
        let api_res = res;
        this.refreshToken(res);
        this.hideLoader();

        if (api_res.responseCode >= 0) {
          // Success case
          this.log(api_res.responseObject );
          if (show_success_notify) {
            this.notify.success('Success', this.getMessage(api_res));
          }
          return api_res.responseObject ;
        } else {
          if (api_res.responseCode == this.unAuthorizedCode && secure) {
            this.handleUnAuthorized();
          }
          // error case

          let error_data;
          if (api_res.responseObject ) { error_data = api_res.responseObject ; }
          throw { 'err_msg': (this.getMessage(api_res)), 'data': error_data, 'status': api_res.status };
        }
      }),
      catchError(this.handleError())
      );
  }



  put(api_key: string, extraParam = '', data, secure = true, show_success_notify = true) {
    if (API_MODE.toString() == 'DEV') {
      this.log(data);
        return this.fetch(api_key, '', show_success_notify);
    }

    this.showLoader();
    let api_url = this.getAPIUrl(api_key);
    if (!api_url) {
      return throwError('Requested API not found:' + api_key);
    }
    if (extraParam !== '') {
      api_url = api_url + extraParam;
    }

    let httpOptions = this.getHeaderOption(secure);



    return this.http.put(api_url, data, httpOptions)
      .pipe(map((res: any) => {
        this.hideLoader();
        let api_res = res;
        this.refreshToken(res);
        if (api_res.responseCode >= 0 ) {


          // success case show success message
          if (show_success_notify) {
            this.notify.success('Success', this.getMessage(api_res));
          }
          return api_res.responseObject ;
        } else {
          if (api_res.code == this.unAuthorizedCode) {
            this.handleUnAuthorized();
          }
          // error case show error message
          let error_data;
          if (api_res.responseObject ) { error_data = api_res.responseObject ; }
          throw { 'err_msg': (this.getMessage(api_res)), 'data': error_data, 'status': api_res.status };
        }
      }),
      catchError(this.handleError())
      );
  }


  fetchPost(api_key: string, data, extraParam = '', secure = true) {
    return this.post(api_key, data, extraParam, secure, false);
  }

  post(api_key: string, data, extraParam = '', secure = true, show_success_notify = true) {
    if (API_MODE.toString() == 'DEV') {
      this.log(data);
      return this.fetch(api_key, '', show_success_notify, secure);
    }

    let api_url = this.getAPIUrl(api_key);
    if (!api_url) {
      return throwError('Requested API not found:' + api_key);
    }

    if (extraParam !== '' && API_MODE.toString() !== 'DEV') {
      api_url = api_url + extraParam;
    }

    let httpOptions = this.getHeaderOption(secure);

    this.showLoader();


    return this.http.post(api_url, data, httpOptions)
      .pipe(map((res: any) => {
        let api_res = res;
        this.hideLoader();
        this.refreshToken(res);
        if (api_res.responseCode >= 0 ) {

          // success case show success message
          if (show_success_notify) {
            this.notify.success('Success', this.getMessage(api_res));
          }
          return api_res.responseObject ;
        } else {
          if (api_res.code == this.unAuthorizedCode) {
            this.handleUnAuthorized();
          }

          // error case show error message
          let error_data;
          if (api_res.responseObject ) { error_data = api_res.responseObject ; }
          throw { 'err_msg': (this.getMessage(api_res)), 'data': error_data, 'status': api_res.status };
        }
      }),
      catchError(this.handleError())
      );
  }




  private handleError() {
    return (error: any) => {
      this.manageErrorNotification(error);
      const errParam = { 'code': 'ERROR_GENERAL' };
      if (error) {
        throw error;
      } else {
        throw this.getMessage(errParam);
      }
      // throw throwError(error || this.getMessage(errParam));
    };
  }




  postDownload(api_key: string, data, extraParam = '') {
    if (API_MODE.toString() == 'DEV') {
      this.log(data);
      return this.fetch(api_key, '', false);
    }

    let api_url = this.getAPIUrl(api_key);
    if (!api_url) {
      return throwError('Requested API not found:' + api_key);
    }

    
    if (extraParam !== '' && API_MODE.toString() !== 'DEV') {
      api_url = api_url + extraParam;
    }

    let httpOptions = this.getHeaderOption();
    httpOptions['responseType'] = 'blob';

    this.showLoader();

    return this.http.post(api_url, data, httpOptions).pipe(map((res: any) => {
      this.hideLoader();
      this.refreshToken(res);
      return res;
    }),
    catchError(this.handleError())
  );
}

  postDownloadStream(api_key: string, data, extraParam = '') {

    if (API_MODE.toString() == 'DEV') {
      this.log(data);
      return this.fetch(api_key, '', false);
    }

    let api_url = this.getAPIUrl(api_key);
    if (!api_url) {
      return throwError('Requested API not found:' + api_key);
    }

    if (extraParam !== '' && API_MODE.toString() !== 'DEV') {
      api_url = api_url + extraParam;
    }
    let httpOptions = this.getHeaderOption();
    httpOptions['responseType'] = 'blob';

    this.showLoader();

    return this.http.post(api_url, data, httpOptions).pipe(map((res: any) => {
      this.hideLoader();
      this.refreshToken(res);
      return res.text();
    }),
    catchError(this.handleError())); 
  }


  private getAPIUrl(key: string) {

    if (Object.keys(this.apiLists).length === 0) {
      Object.keys(APIConfigurations).forEach((key, index) => {
        Object.keys(APIConfigurations[key]).forEach((subkey, subindex) => {
          this.apiLists[subkey] = APIConfigurations[key][subkey];
        });
      });
    }

    // code to remove DEV. if any
    key = key.replace('DEV.', '');
    // END

    let host = this.API_HOST_JSON;
    // new code
    if (API_MODE.toString() === 'DEV' && typeof this.apiLists['DEV.' + key] !== 'undefined') {
      key = 'DEV.' + key;
      host = '';
    }
    return host + this.apiLists[key];
    // end

  }

  private handleUnAuthorized() {
    this._cookie.remove('authToken');
    setTimeout(() => {
      if (this.startup.enabledSSO) {
        this.startup.logoutSSO(false);
      } else {
        window.location.href = 'index.html';
      }
    }, 2000);
  }


  private getMessage(api_res) {

    let code = api_res.responseCode;

    let code_str = code;
    if (code && ['ERROR_GENERAL', 'FILE_NOT_FOUND'].indexOf(code) === -1) {
      code_str = Number(code).toString();
    } else {
      code = 'Error';
    }
    let key = 'msg_' + code_str;
    let error_code = api_res.responseCode >= 0  ? '' : (code + ': ');
    if (!code || typeof commonMessage[key] === 'undefined') {
      return error_code + (api_res.responseMessage  ? api_res.responseMessage .replace(/\n/ig,'<br/>') : commonMessage['msg_ERROR_GENERAL']);
    } else {
      return error_code + commonMessage[key];
    }
  }
  private showLoader() {
    this.activeCalls++;
  }
  private hideLoader() {
    if (this.activeCalls > 0) {
      this.activeCalls--;
    }
  }

  refreshToken(res) {
    if (res && res.headers && res.headers.get('token')) {
      const token = res.headers.get('token');
      if (token !== '') {
        this._cookie.put('authToken', res.headers.get('token'));
      }
    }
  }

  log(msg) {
    if (API_MODE.toString() === 'DEV') {
      console.log(msg);
    }
  }

  private manageErrorNotification(error) {
    this.hideLoader();
    const errParam = { 'code': 'ERROR_GENERAL' };
    const errMsg = error && error.err_msg ? error.err_msg : this.getMessage(errParam);
    if (error.error && error.error.status) {
      switch (error.error.status || 'undefined') {
        case 'invalid1':
          this.notify.alert('Error', errMsg);
          break;
        case 'info':
          this.notify.info('Note', errMsg);
          break;
        case 'warn':
          this.notify.alert('Warning', errMsg);
          break;
        case undefined:
        default:
          this.notify.error('Error', errMsg);
      }
    } else {
      this.notify.error('Error', errMsg);
    }
  }


  set activeCalls(val) {
    this._activeCalls = val;
    if (document.getElementById('loader')) {
      if (val > 0) {
        document.getElementById('loader').style.display = 'block';
      } else {
        document.getElementById('loader').style.display = 'none';
      }
    }
  }

  get activeCalls() {
    return this._activeCalls;
  }


}

