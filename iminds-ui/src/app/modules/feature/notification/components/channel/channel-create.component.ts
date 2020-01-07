
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { Router } from '@angular/router';
import { NotificationService } from '../../services/notification.service';
import { Channel } from '../../models/channel.model';
import { takeUntil } from 'rxjs/operators';


@Component({
    selector: 'stl-channel-create.component',
    templateUrl: './channel-create.component.html'
})

export class ChannelCreateComponent extends MasterComponent implements OnInit, OnDestroy {

    channelModel: Channel
    channelAttributeList = [];
    channelAttr = { 'attributeName': '', 'attributeValue': '' };
    searched = false;
    totalItems = 0;
    status = true;
    constructor(public service: NotificationService, private router: Router) {
        super();
    }
   
    ngOnInit() {
        this.channelModel = new Channel();
    }

    updateNameFromAlias() {
        if (this.channelModel != null && this.channelModel.name != null && this.channelModel.name != '') {
            this.channelModel.id = this.channelModel.name.toUpperCase();
        }
    }

    updateAliasFromAlias() {
        if (this.channelModel != null && this.channelModel.id != null && this.channelModel.id != ''
            && this.channelModel.id !== this.channelModel.name) {
            this.channelModel.id = this.channelModel.id.toUpperCase();
        }
    }
    getSupportedAttribute() {
        if (this.channelModel.className != null && this.channelModel.className != '') {
            this.service.getSupportedAttribute('/'+this.channelModel.className)
                .pipe(takeUntil(this.onDestroy$))
                .subscribe(res => {
                    this.searched = true;
                    this.channelAttributeList = res;
                    this.totalItems = this.channelAttributeList.length;
                });
        }

    }

    onSubmit(isValid: boolean) {
        // check form validation
        if (!isValid) {
            return;
        }
        this.channelModel.status = this.status ? 'SHOW' : 'HIDE';
        let channleAttr=[];
        if(this.totalItems>0){
            for(let i=0;i<this.totalItems;i++){
                channleAttr.push({ 'attributeName':  this.channelAttributeList[i].displayName, 'attributeValue': this.channelAttributeList[i].value }); 
            }
        }
        this.channelModel.channelAttribute=channleAttr;

        this.service.createChannel(this.channelModel)
            .pipe(takeUntil(this.onDestroy$))
            .subscribe(res => {
                if (!res['err_msg']) {
                    //error
                }
            });
    }


    ngOnDestroy() {
        this.manageDestroy();
    }

}