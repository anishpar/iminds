import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import { NotificationService } from '../../services/notification.service';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';



@Component({
  selector: 'stl-event-configuration-search',
  templateUrl: './event-configuration-search.component.html'
})

export class EventConfigurationSearchComponent extends MasterComponent
  implements OnInit, OnDestroy {
  totalItems;
  candidates = [];

  constructor(public service: NotificationService,public dataStoreService :DataStoreService) {
    super();
  }

  ngOnInit() {
    this.totalItems = 0;
     this.service.searchCandidate()
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        if (this.candidates) {
          this.totalItems = res.length;
          this.candidates = res;    
        }


      }); 
  }
  
  ngOnDestroy() {
    this.manageDestroy();
  } 


}

