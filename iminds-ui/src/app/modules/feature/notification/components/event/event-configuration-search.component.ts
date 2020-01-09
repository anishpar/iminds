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
  candidates = [];
  candidate;
  totalItems ;
  
  constructor(public service: NotificationService,public dataStoreService :DataStoreService) {
    super();
  }

  ngOnInit() {

     this.service.searchCandidate()
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.candidates = res;
        this.candidate = this.candidates[0];
      }); 
  }

  ngOnDestroy() {
    this.manageDestroy();
  } 


}

