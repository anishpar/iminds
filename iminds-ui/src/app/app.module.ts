import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER, Injector } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './modules/core/common/components/app.component';
import { StartupService } from './modules/core/util/services/startup.service';
import { LoginModule } from './modules/core/login/login.module';

import { ServiceLocator } from './modules/core/util/services/locator.service';
import { UtilModule } from './modules/core/util/util.module';
import { CommonCoreModule } from './modules/core/common/common-core.module';

import { KeycloakAngularModule } from 'keycloak-angular';

import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './modules/core/common/components/page.not.found.component';
import { RouteGuardService } from './modules/core/common/services/route.guard.service';

import { LoginComponent } from './modules/core/login/components/login.component';
import { LoginRouteGuardService } from './modules/core/common/services/login-route.guard.service';
import { HomeComponent } from './modules/core/common/components/home.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  { path: 'home', pathMatch: 'full', component: HomeComponent },
  // Sample feature module
  { path: 'staff', loadChildren: () => import('./modules/feature/staff/staff.module').then(m => m.StaffModule),
  canActivateChild: [RouteGuardService] },

  // notification modle routing
  { path: 'notification', loadChildren: () => import('./modules/feature/notification/notification.module').then(m => m.NotificationModule),
  canActivateChild: [RouteGuardService] },

  { path: 'login', component: LoginComponent, canActivate: [LoginRouteGuardService] },

  { path: '404', component: PageNotFoundComponent },


  { path: '**', pathMatch: 'full', component: PageNotFoundComponent }
];



export function startupServiceFactory(startupService: StartupService): Function {
  return () => startupService.load();
}


@NgModule({
  declarations: [],
  imports: [
    BrowserModule, CommonCoreModule
    , UtilModule
    , LoginModule, HttpClientModule, KeycloakAngularModule,
    RouterModule.forRoot(routes, { useHash: true})
  ],
  providers: [
    {
        // Provider for APP_INITIALIZER
        provide: APP_INITIALIZER,
        useFactory: startupServiceFactory,
        deps: [StartupService],
        multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private injector: Injector) {    // Create global Service Injector.
    ServiceLocator.injector = this.injector;
  }
 }
