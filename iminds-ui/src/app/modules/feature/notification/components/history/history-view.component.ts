import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {Router} from "@angular/router";
import { NotificationService } from '../../services/notification.service';
import { HistoryModel } from '../../models/history.model';
import { HistoryReceiverModel } from '../../models/history-receiver.model';
import { HistoryDetailsModel } from '../../models/history-details.model';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';

@Component({
    selector: 'stl-history-view.component',
    templateUrl: './history-view.component.html'
  })

  export class ViewHistoryComponent extends MasterComponent implements OnInit, OnDestroy{
    public historyData=new HistoryModel();
    language = '';
    channel :String = '';
    recipient :String = '';
    status :String = '';
    messageContent = '';
    statusMessage = '';
    lastProcessDate = '';
    languageListFromAPI = [];
    configurationData = ['languages'];
    historyList;
    
    constructor(public service: NotificationService,private route:ActivatedRoute,private router: Router ,public dataStoreService :DataStoreService) {
        super();
      }

    ngOnInit() {
      let receiver = [];
      let tempRecipient;
      let tempDispatchStatus;

      this.historyList = this.getNavParam('transectionId');   
      if(  this.historyList  != null ){
        let transectionId =    this.historyList.transectionId;
        this.historyData = this.dataStoreService.getData(transectionId);
        if (this.historyData ==null) {
          // redirect to search page
          this.router.navigate(['notification/search_history'])
        }

      for (let i = 0; i < this.historyData.communicationDetails.length; i++) {
        receiver = [];
        receiver = this.historyData.communicationDetails[i].receiver;

          for (let j = 0; j < receiver.length; j++) {
            tempRecipient = receiver[j].communicationAddress;
            tempDispatchStatus = receiver[j].status;

            if(this.historyList.recipient === tempRecipient && this.historyList.dispatchStatus === tempDispatchStatus){
              this.channel = this.historyData.communicationDetails[i]["@type"];
              this.recipient = tempRecipient;
              this.status = tempDispatchStatus;
              this.messageContent = this.historyData.communicationDetails[i].content;
              this.statusMessage = receiver[j].statusMessage;
              this.lastProcessDate = receiver[j].sendTimeComplete;
        
              this.service.loadConfiguration(this.configurationData)
              .pipe(takeUntil(this.onDestroy$))
              .subscribe(res => {
        
                //filter language cache data to get name of language
                this.languageListFromAPI = res['languages'];
                if(this.historyData.language !=null){
                  this.languageListFromAPI = this.languageListFromAPI.filter(s => s.alias === this.historyData.language);
                  this.language = this.languageListFromAPI[0].name;
                }else{
                  this.language = "-";
                }  
              });
            }
          }
         }   
       } 
   }
    
    ngOnDestroy() {
      this.manageDestroy();
    }
}
