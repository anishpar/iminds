import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { MasterComponent } from '../../common/components/master.component';
import { StlCookieService } from '../../util/services/stl-cookie.service';
import { takeWhile } from 'rxjs/operators';

@Component({
  selector: 'stl-login',
  templateUrl: './login.component.html'
})
export class LoginComponent extends MasterComponent implements OnInit, OnDestroy {
  public login = { 'username': '', 'password': '', 'sId': this.config.sId };
  public isLoggeIn = false;
  public customerDetail;
  public custAccountNumber;
  public isRedirect = false;
  onPage = true;

  constructor(private router: Router, private _cookie: StlCookieService) {
    super();
    sessionStorage.clear();
    localStorage.clear();
  }
  ngOnInit() { }
  onSubmit(isValid) {
    if (!isValid) {
      return;
    }
    this._auth.login(this.login).pipe(takeWhile(() => this.onPage)).subscribe((res) => {
      if (typeof res.token !== 'undefined') {
        this._cookie.put('authToken', res.token);
        this._auth.storeLoginData(this.login.username, res.staffName, res.type)
        this.router.navigate(['/home']);
      }
    });
  }

  ngOnDestroy() {
    this.onPage = false;
  }

}
