import { Component, OnInit } from '@angular/core';
import { MasterComponent } from './master.component';
import { Router } from '@angular/router';

@Component({
  selector: 'stl-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent extends MasterComponent implements OnInit {
  collepsedNotification = false;
  sideBarClosed = false;

  constructor( private router: Router) { 
    super();
  } 

  ngOnInit() {
  }
  logout() {
    this._auth.logout();
  }

  navigate(route:string) {
    this.router.navigate([route]);
    if (this.sideBarClosed)
    this.collepsedNotification = !this.collepsedNotification;
  }

  manageSidebar() {
    this.sideBarClosed = !this.sideBarClosed
    if (this.sideBarClosed) {
      this.collepsedNotification = true;
    } else {
      this.collepsedNotification = false;
    }
  }

  toggleSubMenu() {
    this.collepsedNotification=!this.collepsedNotification;
    if (this.sideBarClosed && !this.collepsedNotification) {
      setTimeout(() => {
        if (this.sideBarClosed && !this.collepsedNotification) {
          this.collepsedNotification = true;
        }
      }, 5000);
    }
  }
}
