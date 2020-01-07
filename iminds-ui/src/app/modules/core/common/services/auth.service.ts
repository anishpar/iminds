import { CrudeService } from '../../util/services/crude.service';
import { Injectable, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { StlCookieService } from '../../util/services/stl-cookie.service';
import { StartupService } from '../../util/services/startup.service';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  public menuList = [];
  public accessList = [];
  private pageAccessList = ['home', 'report'];
  public pageListMap: Map<any, any>;
  public pageAccessedList = [];
  public staffName;
  public type;
  public dataEmiter$: EventEmitter<any>;
  public customCurrencyMap = {};
  public pageTitle = '';
  public pageTitleMap = {};
  public csv_data;
  public startupConfig: any;

  constructor(public _service: CrudeService, private _cookie: StlCookieService, private router: Router,
    private startup: StartupService, protected keycloakAngular: KeycloakService) {
    this.pageListMap = new Map();
    this.dataEmiter$ = new EventEmitter();
    this.startupConfig = this.startup.configuration;
  }
  getMenuList() {
    return this._service.fetch('LOAD_MENU').toPromise().then((res) => {

      let menuJson = res;
      this.menuList = menuJson.menulist;

      for (let i = 0; i < this.menuList.length; i++) {
        let menuModule = this.menuList[i];

        if (menuModule.alias && this.accessList.indexOf(menuModule.alias) === -1) {
          this.accessList.push(menuModule.alias);
        }

        this.pageListMap.set('page_' + menuModule.actionid, menuModule);

        let module = menuModule.url;

        if (menuModule.children && menuModule.children.length > 0) {
          for (let j = 0; j < menuModule.children.length; j++) {
            let menuLink = menuModule.children[j];

            if (this.accessList.indexOf(menuLink.alias) === -1) {
              this.accessList.push(menuLink.alias);
            }
            this.pageAccessList.push(module + '/' + menuLink.url);
            this.pageListMap.set('page_' + menuLink.actionid, menuLink);
            if (menuLink.url.indexOf('/') !== -1) {
              this.pageListMap.set('page_' + menuLink.url.split('/').pop(), menuLink);
            } else {
              this.pageListMap.set('page_' + menuLink.url, menuLink);
            }
          }
        }
       

      }
    });
  }
  
  login(loginData) {
    return this._service.post('LOGIN', loginData, '', false, false);
  }

  isLoggedIn(): boolean {
    if (this._cookie.get('authToken')) {
      return true;
    }
    return false;
  }

  logout(redirect = true) {
    this.clearSession(redirect);
    // this._service.fetch('LOGOUT').subscribe(() => {}, () => {}, () => {
    // });
  }

  clearSession(redirect) {
    this._cookie.remove('authToken');
      this._cookie.removeAll();
      // reload and redirect after logout
      if (redirect) {
        if (this.startup.enabledSSO) {
           this.keycloakAngular.logout(this.startup.configuration['redirectUrl']);
        } else {
          window.location.href = 'index.html';
        }
     }
  }

  // method to check user access
  hasPermission(accessKey: string): boolean {
    return (this.accessList.indexOf(accessKey) !== -1);
  }


  getAccess(url: string) {

    if (url === '/') {
      return;
    }
    const temp_arr = url.split('/');
    const fullUrl = (temp_arr.length > 2) ? temp_arr[1] + '/' + temp_arr[2] : temp_arr[1];
    const urlParam = temp_arr[1];

    if (this.pageAccessedList.indexOf(urlParam) === -1) {
      this._service.fetchPost('GET_ACCESS', { 'url': urlParam }).subscribe((res) => {
        this.pageAccessedList.push(urlParam);
        if (res.menulist) {
          res.menulist.forEach((item) => {
            this.setChildAccess(item, urlParam);
          });

          if (this.pageAccessList.indexOf(fullUrl) === -1) {
            this.redirectNoAccess(fullUrl);
          }
          this.dataEmiter$.emit(this.pageAccessList);
        } else {
          this.redirectNoAccess(fullUrl);
        }
      }, () => {
        this.redirectNoAccess('');
      });
    } else {
      if (this.pageAccessList.indexOf(fullUrl) === -1) {
        this.redirectNoAccess(fullUrl);
      }
    }

  }

  private redirectNoAccess(fullUrl) {
    console.log('No Page Access ' + fullUrl);
    if (fullUrl !== 'home') {
      this.router.navigate(['/404']);
    }
  }

  setChildAccess(item, urlParam) {
    if (this.accessList.indexOf(item.alias) === -1) {
      this.accessList.push(item.alias);
      this.pageListMap.set('page_' + item.actionid, item);
      if (item.url) {
        this.pageTitleMap[item.alias] = item.name;
        if (item.url.indexOf('/') !== -1) {
          this.pageListMap.set('page_' + item.url.split('/').pop(), item);
        } else {
          this.pageListMap.set('page_' + item.url, item);
        }
      }
    }
    let pageUrl = urlParam + '/' + item.url;
    if (this.pageAccessList.indexOf(item.url) === -1) {
      this.pageAccessList.push(pageUrl);
    }
    if (item.children) {
      item.children.forEach((subItem) => this.setChildAccess(subItem, urlParam));
    }
  }

  getAccessList() {
    return this.accessList;
  }

  getPageAccessList() {
    return this.pageAccessList;
  }

  getUserDetails() {
    return this._service.fetch('LOGIN_USER_DETAIL');
  }

  storeLoginData(username: string, staffName: string, type: string) {
    sessionStorage.setItem('username', username);
    sessionStorage.setItem('staffName', staffName);

    this._cookie.put('staffName', staffName);
    this.staffName = staffName;

    if (type) {
      sessionStorage.setItem('type', type);
      this._cookie.put('type', type);
      this.type = type;
    }
  }

  isSSOLoggedIn() {
    return this.keycloakAngular.isLoggedIn();
  }

  get enabledSSO() {
    return this.startup.enabledSSO;
  }

  checkActionAccess(elementList: Array<string>) {
    return this._service.fetchPost('CHECK_USER_ACCESS', elementList);
  }

}
