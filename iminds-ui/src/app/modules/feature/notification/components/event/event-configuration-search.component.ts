import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import { NotificationService } from '../../services/notification.service';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';



@Component({
  selector: 'stl-event-configuration-search',
})

export class EventConfigurationSearchComponent extends MasterComponent
  implements OnInit, OnDestroy {
  candidate = [];
  
  constructor(public service: NotificationService,public dataStoreService :DataStoreService) {
    super();
  }

  ngOnInit() {

     this.service.searchCandidate()
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.candidate = res;
      }); 
  }

  ngOnDestroy() {
    this.manageDestroy();
  } 


}

