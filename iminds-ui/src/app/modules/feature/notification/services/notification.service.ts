import { Injectable } from '@angular/core';
import { CrudeService } from '../../../core/util/services/crude.service';

@Injectable({providedIn: 'root'})
export class NotificationService {
  constructor(public _service: CrudeService) {
  }

  createTemplate(templateData) {
    return this._service.post('CREATE_TEMPLATE', templateData);
  }

  viewTemplate(templateName){
    return this._service.fetch('GET_FILTERED_TEMPLATE','?name='+templateName);
  }
  

  searchTemplate(templateModel) {
    
    
    let queryParam = '?status='+templateModel.status;
        if(templateModel.eventAlias != null && templateModel.eventAlias != "")
            queryParam = queryParam + '&eventAlias='+templateModel.eventAlias;
        if(templateModel.channelAlias != null && templateModel.channelAlias != "")
            queryParam = queryParam + '&channelAlias='+templateModel.channelAlias;
        if(templateModel.name != null && templateModel.name != ""){
          console.log("Template Model NAME  : == " + templateModel.name);
          queryParam = queryParam + '&name='+templateModel.name;
        }
        console.log("Template Model QueryParam  : == " + queryParam);
    return this._service.post('GET_FILTERED_TEMPLATE', null);
  }

  searchJob(jobRequest) {
    let queryParam = '';
    if(jobRequest.location != null && jobRequest.location != "")
        queryParam = queryParam + '&location='+jobRequest.location;
    if(jobRequest.title != null && jobRequest.title != "")
        queryParam = queryParam + '&title='+jobRequest.title;
    console.log("Template Model QueryParam  : == " + queryParam);
  return this._service.fetch('GET_FILTERED_TEMPLATE', null);
  }

  loadConfiguration(configurationData) {
    return this._service.fetchPost('LOAD_CONFIGURATION', configurationData);
  }

  getTemplatesByFilter(templateFilterData){
    return this._service.fetch('GET_FILTERED_TEMPLATE', templateFilterData);
  }
  
  searchHistory(historyModel) {  
        let queryParam = '?fromDate='+historyModel.dateRange.fromDate+'&toDate='+historyModel.dateRange.toDate;
        if(historyModel.eventAlias != null && historyModel.eventAlias != "")
            queryParam = queryParam + '&eventAlias='+historyModel.eventAlias;
        if(historyModel.channelAlias != null && historyModel.channelAlias != "")
            queryParam = queryParam + '&channelAlias='+historyModel.channelAlias;
        if(historyModel.dispatchStatus != null && historyModel.dispatchStatus != "")
            queryParam = queryParam + '&status='+historyModel.dispatchStatus;
        if(historyModel.transectionId != null && historyModel.transectionId != "")
            queryParam = queryParam + '&transectionId='+historyModel.transectionId;
        if(historyModel.requestIdentifier != null && historyModel.requestIdentifier != "")
            queryParam = queryParam + '&requestIdentifier='+historyModel.requestIdentifier;        

    return this._service.fetch('SEARCH_HISTORY', queryParam);
  }
  
  createEventTemplateConfiguration(eventTemplateAssociation){
    return this._service.post('EVENT_TEMPLATE_CONFIGURATION', eventTemplateAssociation);
  }

  getEventAssociationByFilter(eventFilterData){
    return this._service.fetch('EVENT_TEMPLATE_CONFIGURATION', eventFilterData);
  }

  getEventTags(eventAlias){
    return this._service.fetch('GET_EVENT_TAGS', eventAlias);
  }

  reProcessNotification(historyModel){
    return this._service.post('REPROCESS_NOTIFICATION', historyModel);
  }
  getRemainingEvent(channelAlias){
    return this._service.fetch('GET_REMAINING_EVENTS', channelAlias);
  }
  
  validateExpression(expression){
    return this._service.fetchPost('VALIDATE_EXPRESSION', expression);
  }

  updateEventTemplateConfiguration(eventTemplateAssociation){
    return this._service.put('EVENT_TEMPLATE_CONFIGURATION','', eventTemplateAssociation);
  }

  getSupportedAttribute(className){
    return this._service.fetch('GET_SUPPORTED_ATTRIBUTES',className);
  }
  createChannel(channel){
    return this._service.post('CHANNEL',channel);
  }

  searchCandidate(){
    return this._service.fetch('SEARCH_CANDIDATE');
  }

  prepareExpression(expressionInput) {
    //prepare expression
    let expression = '';
    let newExp = JSON.parse(JSON.stringify(expressionInput));
    for(let i=0; i < expressionInput.length; i++) {
      let currentNode = expressionInput[i];
      
      let prevNode, nowNode, nextNode = null;
      if (newExp[i-1]) prevNode = newExp[i-1];
      
      if (newExp[i]) nowNode = newExp[i];

      if (newExp[i+1]) nextNode = newExp[i+1];
      if (currentNode['mode'] == 2) {
        

        switch(currentNode['id']) {
          case 'DoesNotBeginWith':
              if (prevNode) {
                prevNode.text = 'NOT(' + prevNode.text;
              }
              if (nextNode)
                nextNode.text = '"'+nextNode.text+'*")';
              nowNode.text = '=';
            break;
            case 'DoesNotContains':
                if (prevNode) {
                  prevNode.text = 'NOT(' + prevNode.text;
                }
                if (nextNode)
                  nextNode.text = '"*'+nextNode.text+'*")';
                nowNode.text = '=';
              break;
            case 'DoesNotEqual':
                if (prevNode) {
                  prevNode.text = 'NOT(' + prevNode.text;
                }
                if (nextNode)
                  nextNode.text = '"'+nextNode.text+'")';
                nowNode.text = '=';
              break;
  
          case 'Begins With':
              if (nextNode)
                nextNode.text = '"'+nextNode.text+'*"';
              nowNode.text = '=';
            break;
          case 'Contains':
              if (nextNode)
                nextNode.text = '"*'+nextNode.text+'*"';
              nowNode.text = '=';
            break;
          case 'Ends With':
              if (nextNode)
                nextNode.text = '"*'+nextNode.text+'"';
              nowNode.text = '=';
            break;
          case 'Equals':
              if (nextNode)
                nextNode.text = '"'+nextNode.text+'"';
              nowNode.text = '=';
            break;
          case 'Less Than':
              if (nextNode)
                nextNode.text = '"'+nextNode.text+'"';
              nowNode.text = '<';
            break;
          case 'Greater Than':
              if (nextNode)
                nextNode.text = '"'+nextNode.text+'"';
              nowNode.text = '>';
            break;
        }
      } else if (currentNode['mode'] == 1 && nowNode.text != '(' && nowNode.text != ')') {
        nowNode.text = '"${'+nowNode.text+'}"';
      }
    }
    
    newExp.forEach(s =>  {expression += s.text+" " });
    
    return expression;
  }

}
