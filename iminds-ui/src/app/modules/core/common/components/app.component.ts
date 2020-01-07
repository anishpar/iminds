import { Component, OnInit, HostListener, ViewChild, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { MasterComponent } from './master.component';
import { StartupService } from '../../util/services/startup.service';
import { filter, map } from 'rxjs/operators';
import { StlCookieService } from '../../util/services/stl-cookie.service';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'stl-root',
  templateUrl: './app.component.html'
})
export class AppComponent extends MasterComponent implements OnInit, OnDestroy {
  public changebar = true;
  public currentYear;
  isLoggedIn = false;
  public notificationConfig = {
    position: ['top', 'right'],
    timeOut: 10000,
    showProgressBar: true,
    pauseOnHover: true,
    maxStack: 1,
    theClass: 'stlNotification',
    clickToClose: true
  };
  public loginuser;
  public sessionstaffName;
  public enabledSSO = false;


  constructor(private router: Router, private _cookie: StlCookieService,
    private startup: StartupService, protected keycloakAngular: KeycloakService) {
    super();
  }
  @HostListener('window:keydown', ['$event'])
  keyboardInput(event: any) {
    if (this._auth._service.activeCalls > 0) {
      event.preventDefault();
    }
  }
  ngOnInit() {
    this.currentYear = new Date().getFullYear();
    this.sessionstaffName = sessionStorage.getItem('staffName');

    if (this.startup.startupData) {

      this._auth._service.API_HOST_JSON = this.startup.startupData['api_host'];


      if (this.startup.enabledSSO) {
          this.enabledSSO = true;
        this.keycloakAngular.isLoggedIn().then( isLoggedIn => {
          if ( !isLoggedIn ) {
            this.keycloakAngular.login( { redirectUri: this.startup.configuration['redirectUrl'] } );
            return;
          } else {
            this.keycloakAngular.getToken().then( token => {

              this._cookie.put('authToken', token);
              // this.router.navigate(['/home']);

                // this._auth.getUserDetails().subscribe(res => {
                //   this._auth.storeLoginData(res.userName, res.staffName, res.type);
                // });

              this.isLoggedIn = true;
              this.setRouteCheck();
            }, error => {

            } );
          }
        });
      } else {

        this.isLoggedIn = this._auth.isLoggedIn();
        this.setRouteCheck();

        if (this.isLoggedIn) {
          this.loginuser =
          this._auth.staffName ? this._auth.staffName : (sessionStorage.getItem('staffName') ?
          sessionStorage.getItem('staffName') : this._cookie.get('staffName'));
        } else {
          this.router.navigate(['/login']);
        }

      }


    }

  }
  

  private setRouteCheck() {

    this.router.events.pipe(
      filter((event) => event instanceof NavigationEnd),
      map(e => e as NavigationEnd))
      .subscribe(event => {
        this.isLoggedIn = this._auth.isLoggedIn();
        if (this.isLoggedIn && event.url === '/login') {
          // redirecting user on home if token is available
          this.router.navigate(['/home']);

          // this._auth.logout(false);
          // this.isLoggedIn = false;
          return;
        } else if (event.url !== '/login'
          && event.url !== '/logout'
          && event.url !== '/home'
          && event.url !== '/404' && event.url !== '/staff/mywork'
          ) {
          // this._auth.getAccess(event.url);
        }
      });

  }

  logout() {
    this._auth.logout();
  }

  ngOnDestroy() {
    this.manageDestroy();
  }


}
