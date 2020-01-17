import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { NotificationService } from '../../services/notification.service';
import { Channel } from '../../models/channel.model';
import { ActivatedRoute } from '@angular/router';
import { Router } from "@angular/router";
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';
import { takeUntil } from 'rxjs/operators';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
    selector: 'stl-event-configuration-create',
    templateUrl: './event-configuration-create.component.html'
})

export class EventConfigurationCreateComponent extends MasterComponent 
implements OnInit, OnDestroy {
    totalItems;
    fileUrl;
    searched = false;
    candidate;
    channelList = [];
    channelModel: Channel;
    channelMap=[];
    channels = [];
    public channel = new Channel;
    constructor(public service: NotificationService,public dataStoreService :DataStoreService, 
        private route: ActivatedRoute, private router: Router,private sanitizer: DomSanitizer) {
        super();
    }

    ngOnInit() {
        this.candidate = this.getNavParam('CandidateInfo');
        
        
          
    }
    viewChannel(channelName:String){
      
            
    }

    ngOnDestroy() {
        this.manageDestroy();
    }

    downloadFile(){
      console.log("Method Starts");
        const data = 'some text';
        const blob = new Blob([data], { type: 'application/octet-stream' });

        this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));    
    }
}
