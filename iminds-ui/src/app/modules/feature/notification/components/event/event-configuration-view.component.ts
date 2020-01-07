import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import { TemplateModel } from '../../models/template.model';
import { NotificationService } from '../../services/notification.service';
import { EventConfiguration } from '../../models/event-configuration.model';
import { TemplateRelation } from '../../models/event-configuration-template-relation.model';
import { Characteristics } from '../../models/characteristics.model';
import { DataStoreService } from '../../../../core/util/services/data-store.service';
import {ActivatedRoute} from '@angular/router';
import {Router} from "@angular/router"

@Component({
    selector: 'stl-event-configuration-view',
    templateUrl: './event-configuration-view.component.html'
  })

  export class ViewEventConfigurationComponent extends MasterComponent {
    public eventConfig = new EventConfiguration;
    public eventDataList;
    public channelName :String;
    public eventName:String;  
    constructor(public service: NotificationService, private dataService: DataStoreService, private route:ActivatedRoute,private router: Router) {
        super();
      }

      ngOnInit() {
            let cacheKey = this.route.snapshot.params['channelAlias'];
            this.eventDataList = this.dataService.getData(cacheKey);
            this.channelName =  this.eventDataList.channelAlias;
            this.eventName = this.eventDataList.eventAlias;
            let  eventChannelName = this.dataService.getData(cacheKey + '##');
            if (eventChannelName != null && eventChannelName != '') {
              let split = eventChannelName.split('##');
              if (split[0] != '') {
                this.channelName = split[0]
              }
      
              if (split[1] != '') {
                this.eventName = split[1]
              }
            }

            if(this.eventDataList == null){
            	this.router.navigate(['notification/search_event_configuration'])
            }
    }
    
    update(){
      this.router.navigate(['/notification/update_event_configuration',this.eventDataList.channelAlias]);
    }

}
