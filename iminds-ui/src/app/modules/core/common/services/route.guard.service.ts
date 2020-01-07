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
export class RouteGuardService implements CanActivate, CanActivateChild {


  constructor( private router: Router, private _auth: AuthService) {}


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let url: string = state.url;
    return this.hasAccess(url);
  }


  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.canActivate(route, state);
  }


  hasAccess(url: string): boolean | Promise<boolean> {
    if ( this._auth.enabledSSO) {
      return this._auth.isSSOLoggedIn().then(isLoggedIn => {
          return isLoggedIn;
      });
    } else {
      if (!this._auth.isLoggedIn()) {
        this.router.navigate(['/login']);
        return false;
      }
      return true;
    }
  }
}
