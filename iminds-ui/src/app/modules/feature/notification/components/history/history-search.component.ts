import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { OnInit, OnDestroy, Component } from '@angular/core';
import { HistoryModel } from '../../models/history.model';
import { DateRangeModel } from '../../../shared/models/date-range.model';
import { NotificationService } from '../../services/notification.service';
import { takeUntil } from 'rxjs/operators';
import { PaginationConfig } from 'src/app/modules/core/util/configuration/pagination.config';
import { HistoryReceiverModel } from '../../models/history-receiver.model';
import { HistoryDetailsModel } from '../../models/history-details.model';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';

@Component({
  selector: 'stl-history-search',
  templateUrl: './history-search.component.html'
})

export class SearchHistoryComponent extends MasterComponent
  implements OnInit, OnDestroy {
  configurationData = ['events', 'channels', 'DISPATCH_STATUS'];
  historyModel = new HistoryModel();
  channels = [];
  events = [];
  dispatchStatus = [];
  pagination;
  totalItems;
  searched = false;
  historyList = [];
  checkAll;

  constructor(public service: NotificationService,public dataStoreService :DataStoreService) {
    super();
    this.pagination = Object.assign({}, PaginationConfig);
  }

  ngOnInit() {
    this.checkAll = false;
    let curDate: Date = new Date();
    let daysBack: Date = new Date(curDate.getTime() - (6 * 24 * 60 * 60 * 1000));
    this.historyModel.dateRange = new DateRangeModel();
    this.service.loadConfiguration(this.configurationData)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.channels = res['channels'];
        this.historyModel.channelAlias = '';
        this.events = res['events'];
        this.historyModel.eventAlias = '';
        this.dispatchStatus = res['DISPATCH_STATUS'];
        this.historyModel.dispatchStatus = '';
      });      
      this.historyModel.dateRange = { "fromDate": this.dateToCSVFormat(daysBack), "toDate": this.dateToCSVFormat(curDate) } ;
  }
  ngOnDestroy() {
    this.manageDestroy();
  }

  onSubmit(isValid: boolean) {
    this.checkAll = false;
    let tempHistoryList = [];
    let indexCount = 0;
    let tempTransactionId;
    let tempRecipient;
    let tempEvent;
    let tempChannel;
    let tempRequestIdentifier;
    let tempLastProcessDate;
    let tempDispatchStatus;
    let communicationDetails = [];
    let receiver = [];
    // check form validation
    if (!isValid) {
      return;
    }

    this.totalItems = 0;
    this.pagination = Object.assign({}, PaginationConfig);
    this.historyList = [];
    this.service.searchHistory(this.historyModel)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.searched = true;

        if (this.historyList) {

          this.totalItems = res.length;

          for (let i = 0; i < this.totalItems; i++) {            
            tempTransactionId = res[i].id;
            tempEvent = res[i].eventAlias;
            tempRequestIdentifier = res[i].referenceEntityId;

            //store the history data into cache for view
            this.dataStoreService.setData(tempTransactionId,res[i]);

            communicationDetails = [];
            if (res[i].communicationDetails) {
              communicationDetails = res[i].communicationDetails;

              for (let j = 0; j < communicationDetails.length; j++) {
                tempChannel = communicationDetails[j]["@type"];
                receiver = [];
                receiver = communicationDetails[j].receiver;

                for (let k = 0; k < receiver.length; k++) {
                  tempHistoryList = [];
                  tempRecipient = receiver[k].communicationAddress;
                  tempDispatchStatus = receiver[k].status;
                  tempLastProcessDate = receiver[k].sendTimeComplete;
                  tempHistoryList['transectionId'] = tempTransactionId;
                  tempHistoryList['recipient'] = tempRecipient;
                  tempHistoryList['eventAlias'] = tempEvent;
                  tempHistoryList['channelAlias'] = tempChannel;
                  tempHistoryList['requestIdentifier'] = tempRequestIdentifier;
                  tempHistoryList['lastProcessDate'] = tempLastProcessDate;
                  tempHistoryList['dispatchStatus'] = tempDispatchStatus;

                  this.historyList[indexCount] = tempHistoryList;
                  indexCount = indexCount + 1;
                } // receiver
              } // communicationDetails        
            } else {
              tempHistoryList = [];
              tempHistoryList['transectionId'] = tempTransactionId;
              tempHistoryList['recipient'] = '';
              tempHistoryList['eventAlias'] = tempEvent;
              tempHistoryList['channelAlias'] = '';
              tempHistoryList['requestIdentifier'] = tempRequestIdentifier;
              tempHistoryList['lastProcessDate'] = '';
              tempHistoryList['dispatchStatus'] = '';

              this.historyList[indexCount] = tempHistoryList;
              indexCount = indexCount + 1;
            }
          } // totalItems      
        }
      });
  }

  resend() {
    let totalCheckedCount = 0;
    this.historyList.forEach(history =>{ 
      if (history.checked) {
        totalCheckedCount++;
      }
    });

    if(totalCheckedCount == 0){
      alert(this.lang.notification.resendAtleastOneSelection);
    }else{
      var c = confirm(this.lang.notification.total +  totalCheckedCount + this.lang.notification.notificationResendCount);  
      if (c == true) {  
         this.historyList.forEach(history =>{
         
          if (history.checked) {
            let historyModel = new HistoryModel();
    
             let historyReceiver = new HistoryReceiverModel();
             historyReceiver.communicationAddress = history.recipient;
             let historyReceivers = []; 
             historyReceivers.push(historyReceiver);
    
             let historyDetailsModel = new HistoryDetailsModel();
             historyDetailsModel.receiver = historyReceivers;
             historyDetailsModel["@type"] = history.channelAlias;
    
             let historyDetailModelList = []; 
             historyDetailModelList.push(historyDetailsModel);
            
            historyModel.id = history.transectionId;
            historyModel.communicationDetails = history.recipient;
            historyModel.status = history.dispatchStatus;
            historyModel.eventAlias = history.eventAlias;
            historyModel.requestIdentifier = history.requestIdentifier;
            historyModel.communicationDetails = historyDetailModelList;
          
           this.service.reProcessNotification(historyModel)
              .pipe(takeUntil(this.onDestroy$))
              .subscribe(res => {});
          }
        });  
      }
    }
  }

  checkUncheckData() {
    this.historyList.forEach(history =>{
      history.checked = this.checkAll;
    });
  }

  changeIndividual(){
    let totalItems = this.historyList.length
    let totalCheckedCount = 0;
    this.historyList.forEach(history =>{
      if (history.checked) {
        totalCheckedCount++;
      }
    });
    if(totalCheckedCount == totalItems){
      this.checkAll = true;
    }else{
      this.checkAll = false;
    }
  }

  trackByHistoryNo(index, item) {
    return item.id;
  }

  resetSearch() {
    let curDate: Date = new Date();
    let daysBack: Date = new Date(curDate.getTime() - (6 * 24 * 60 * 60 * 1000));
    this.historyModel.eventAlias = '';
    this.historyModel.channelAlias = '';
    this.historyModel.dispatchStatus = '';
    this.historyModel.transectionId = '';    
    this.historyModel.dateRange = { "fromDate": this.dateToCSVFormat(daysBack), "toDate": this.dateToCSVFormat(curDate) } ;
  }
}


