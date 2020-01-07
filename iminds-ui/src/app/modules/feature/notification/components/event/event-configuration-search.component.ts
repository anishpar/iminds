import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import { NotificationService } from '../../services/notification.service';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';
import { PaginationConfig } from 'src/app/modules/core/util/configuration/pagination.config';



@Component({
  selector: 'stl-event-configuration-search',
  templateUrl: './event-configuration-search.component.html'
})

export class EventConfigurationSearchComponent extends MasterComponent
  implements OnInit, OnDestroy {
  configurationData = ['channels', 'events'];
  public searchModel = {
    eventAlias: '',
    channelAlias: ''
  };
  channels = [];
  events = [];
  eventAssociationList = [];
  totalItems;
  pagination;
  searched = false;
  eventMap=[];
  channelMap=[];
  constructor(public service: NotificationService,public dataStoreService :DataStoreService) {
    super();
    this.pagination = Object.assign({}, PaginationConfig);
  }

  ngOnInit() {

    this.service.loadConfiguration(this.configurationData)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.channels = res['channels'];
        this.events = res['events'];
      });
  }

  onChangeEvent() {
    this.searchModel.channelAlias = '';
  }
  
  saveData(channelkey: string,channelName: string,eventName: string){
    this.dataStoreService.setData(channelkey+'##',channelName+'##'+eventName);
  }
  
  onSubmit(isValid: boolean) {
    // check form validation
    if (!isValid) {
      return;
    }
    let eventFilter=''
    if(this.searchModel.eventAlias!=''){
      eventFilter = '?eventAlias=' + this.searchModel.eventAlias
    }
    if(this.searchModel.channelAlias!=''){
      eventFilter =eventFilter + '&channelAlias=' + this.searchModel.channelAlias;
    }
  
    this.service.getEventAssociationByFilter(eventFilter)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.searched = true;
        this.eventAssociationList = res;
        this.totalItems=this.eventAssociationList.length;
        if (this.eventAssociationList!=null && this.totalItems>0) {
          for(let i=0;i< this.totalItems ; i++){
            this.dataStoreService.setData(this.eventAssociationList[i].channelAlias,this.eventAssociationList[i]);
            let event=this.eventAssociationList[i].eventAlias;
            this.events.forEach(ele =>{
              if(ele.alias == event){
                this.eventMap[ele.alias]=ele.name
              }
            });

            let channel=this.eventAssociationList[i].channelAlias;

            this.channels.forEach(ele =>{
              if(ele.alias == channel){
                this.channelMap[ele.alias]=ele.name;
              }
            }) ;
          }
          
        }
      }, err=> this.eventAssociationList=[]);
  }
  cancel(){
    this.searchModel.channelAlias='';
    this.searchModel.eventAlias='';
    this.eventAssociationList=[];
  }

  ngOnDestroy() {
    this.manageDestroy();
  }


}

