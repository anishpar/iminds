
import { DateRangeModel } from '../../shared/models/date-range.model';
import { HistoryDetailsModel } from './history-details.model';

export class HistoryModel {
    public eventAlias: string; // Event Activity              
    public channelAlias: string; // Notification Channel EMAIL, SMS 
    public dispatchStatus: string; // SUCCESS, FAIL  
    public transectionId: String;
    public requestIdentifier: String;
    public recipient: String;
    public dateRange: DateRangeModel;  
    public id: String;
    public status: String; 
    public communicationDetails: HistoryDetailsModel[]; 
    public "@type": String; 
    public language: String;
    public referenceEntityId: String; //To get view data from search 


  }