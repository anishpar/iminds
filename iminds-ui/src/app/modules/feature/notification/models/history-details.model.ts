import { HistoryReceiverModel } from './history-receiver.model';

export class HistoryDetailsModel {
    public subject: string; // Event Activity              
    public content: string; // Notification Channel EMAIL, SMS 
    public receiver: HistoryReceiverModel[];  
    public status: String; 
    public statusMessage: String; 
    public "@type": String; 
  }