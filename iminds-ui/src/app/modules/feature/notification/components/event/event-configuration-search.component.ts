import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import { Job } from '../../models/job.model';

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
  filterpage = false;

  constructor(public service: NotificationService,public dataStoreService :DataStoreService) {
    super();
  }

  ngOnInit() {
    let job = this.getNavParam('jobInfo');
    if(job != null) {
      let jobOpeningId = job.jobOpeningid;
      this.totalItems = 0;
      this.filterpage = true;
      this.service.filterCandidate(jobOpeningId)
       .pipe(takeUntil(this.onDestroy$))
       .subscribe(res => {
         if (this.candidates) {
           this.totalItems = res.length;
           this.candidates = res;    
         }
         console.log(this.candidates);
 
       }); 

    } else {
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
    
  }
  
  ngOnDestroy() {
    this.manageDestroy();
  } 


}

