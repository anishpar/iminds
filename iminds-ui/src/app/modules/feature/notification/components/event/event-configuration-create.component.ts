import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { NotificationService } from '../../services/notification.service';
import { Channel } from '../../models/channel.model';
import { ActivatedRoute } from '@angular/router';
import { Router } from "@angular/router";
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';
import { takeUntil } from 'rxjs/operators';


@Component({
    selector: 'stl-event-configuration-create',
    templateUrl: './event-configuration-create.component.html'
})

export class EventConfigurationCreateComponent extends MasterComponent 
implements OnInit, OnDestroy {
    totalItems;
    searched = false;
    channelList = [];
    channelModel: Channel;
    channelMap=[];
    channels = [];
    public channel = new Channel;
    constructor(public service: NotificationService,public dataStoreService :DataStoreService, private route: ActivatedRoute, private router: Router) {
        super();
    }

    ngOnInit() {
        let channelName = this.getNavParam('channelName');
        this.channel = this.dataStoreService.getData(channelName);
        this.viewChannel(channelName);
          
    }
    viewChannel(channelName:String){
      
            
    }

    ngOnDestroy() {
        this.manageDestroy();
    }
}
