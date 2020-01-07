import { Injectable } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanActivateChild
} from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoginRouteGuardService implements CanActivate {


  constructor( private router: Router, private _auth: AuthService) {}


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let url: string = state.url;
    return this.hasAccess(url);
  }



  hasAccess(url: string): boolean | Promise<boolean> {
    if ( this._auth.enabledSSO) {
      return this._auth.isSSOLoggedIn().then(isLoggedIn => {
        this.router.navigate(['/home']);
          return !isLoggedIn;
      });
    } else {
      if (this._auth.isLoggedIn()) {
        this.router.navigate(['/home']);
        return false;
      }
      return true;
    }
  }
}
