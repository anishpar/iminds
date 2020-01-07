import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { takeUntil } from 'rxjs/operators';
import { TemplateModel } from '../../models/template.model';
import { EventTags } from '../../models/tags.model';
import { NotificationService } from '../../services/notification.service';
import { EventConfiguration } from '../../models/event-configuration.model';
import { TemplateRelation } from '../../models/event-configuration-template-relation.model';
import { Router } from '@angular/router';


@Component({
  selector: 'stl-event-configuration-create',
  templateUrl: './event-configuration-create.component.html'
})

export class EventConfigurationCreateComponent extends MasterComponent
  implements OnInit, OnDestroy {
  configurationData = ['channels', 'RECEIPIENT_TYPE'];
  templateFilter = '';
  eventConfigurationModel = new EventConfiguration();
  expressionList = [];
  templateRelation = new TemplateRelation();
  eventTagsAPIResponse = [];
  eventTag = new EventTags();
  priorityCounter = 0; //priority counter for templates
  defaultTemplateCount = 0; //Maximum one default template is allowed in configuration
  channels = [];
  events = [];
  templates = [];
  receipientType = [];
  receipientAddress = [];
  sendToInput = '';
  expressionInput = [];
  public tagsForEventExpressionBuild = [];
  expressionChanged = false;
  errorMessageForExpression='';
  sendToSelected = [];
  sendToList = [];
  sendToPredefinedAlias = [];
  
  constructor(public service: NotificationService, private router: Router) {
    super();
    this.eventConfigurationModel.templateRelation = [];
    this.templateRelation.sendTo = [];
  }
  
  ngOnInit() {
    
    this.service.loadConfiguration(this.configurationData)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.channels = res['channels'];
        this.eventConfigurationModel.channelAlias = '';
        this.eventConfigurationModel.eventAlias = '';
        this.receipientType = res['RECEIPIENT_TYPE'];
        this.eventConfigurationModel.templateRelation = [];
        let sendToList = [];

        this.sendToPredefinedAlias = [];
        if(this.receipientType) {
          this.receipientType.forEach(ele => {
            sendToList.push({'id': ele.alias, 'text': ele.name })
            this.sendToPredefinedAlias.push(ele.alias);
          });
        }
        this.sendToList = sendToList;

      });
    // this.eventConfiguration.status = 'true';
  }

  
  addTemplate() {

    //set priority for added template
    this.templateRelation.priority = ++this.priorityCounter;
   
    
    this.expressionList.push(this.expressionInput.map(ele => ele.text).join(' '));
    let expression = this.service.prepareExpression(this.expressionInput);
    this.templateRelation.conditionExpression = expression;

    //prepare sendTo Data
    this.prepareCharacteristics();
    this.eventConfigurationModel.templateRelation.push(this.templateRelation);

    if (this.templateRelation.type == this.lang.DefaultAlias) {
      this.defaultTemplateCount++;
    }
    //remove added template from combo list
    this.templates = this.templates.filter(ele => ele.name !== this.templateRelation.name);
    
    //reset fields
    this.templateRelation = new TemplateRelation();
    this.templateRelation.sendTo = [];
    this.receipientAddress = [];
    this.sendToSelected = [];
    this.expressionInput = [];
  }

  validateExpression(){
    //prepare expression
    let expression = this.service.prepareExpression(this.expressionInput);
    this.templateRelation.conditionExpression = expression;

    if(expression && expression.length > 0){
      let templateRel = new TemplateRelation();
      templateRel.conditionExpression = expression;
      templateRel.type = this.config.expressionBasedAlias;
      this.service.validateExpression(templateRel)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        if(res){
          this.expressionChanged = false;
          this.errorMessageForExpression = '';
        }else{
          this.errorMessageForExpression = this.lang.invalidExpression;
          this.expressionChanged = true;
        }
       
      });
    }else{
      this.errorMessageForExpression = this.lang.invalidExpression;
    }
    
 
    
  }

  prepareCharacteristics(){
    if(this.sendToSelected){
      this.sendToSelected.forEach(ele => {
        let type = this.sendToPredefinedAlias.indexOf(ele.id) === -1 ? 'RECIPIENTADDRESS' : 'RECIPIENTTYPE';
        this.templateRelation.sendTo.push({'name': ele.id, 'type': type}); 
      });
    }
  }

  onChangeEvent(){
    
    //reset fields
    this.eventConfigurationModel.templateRelation = [];
    this.templateRelation.name = '';
    this.templateRelation.type = '';
    this.templateRelation.priority = 0;
    this.templateRelation.conditionExpression = '';
    this.templateRelation.sendTo = [];
    this.templates = [];
    this.defaultTemplateCount=0;
    this.priorityCounter=0;
    this.expressionInput = [];
    this.tagsForEventExpressionBuild = [];

    //filter template based on channel and event
    if (this.eventConfigurationModel != null && this.eventConfigurationModel.eventAlias != '' && this.eventConfigurationModel.channelAlias != '') {
      this.templateFilter = '?eventAlias=' + this.eventConfigurationModel.eventAlias + '&channelAlias=' + this.eventConfigurationModel.channelAlias
      +'&status=SHOW';
      this.service.getTemplatesByFilter(this.templateFilter)
        .pipe(takeUntil(this.onDestroy$))
        .subscribe(res => {
          this.templates = res;
        });
    }

    //get Event tags for expression
    this.service.getEventTags('?eventAlias='+this.eventConfigurationModel.eventAlias)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        
        let tagList = [];
        if (res && res.length > 0) {
          this.eventTagsAPIResponse = res;
          for (let index = 0; index < this.eventTagsAPIResponse.length; index++) {
            this.eventTag.id = this.eventTagsAPIResponse[index].tagAlias;
            this.eventTag.text = this.eventTagsAPIResponse[index].tagAlias;
            tagList.push(this.eventTag);
            this.eventTag = new EventTags();
         }
        }
        this.tagsForEventExpressionBuild = tagList;
      });
  }
  
  onChangeChannel() {
    this.priorityCounter--;
    this.eventConfigurationModel.eventAlias = '';
    this.eventConfigurationModel.templateRelation = [];
    this.templateRelation.name = '';
    this.templateRelation.type = '';
    this.templateRelation.priority = 0;
    this.templateRelation.conditionExpression = '';
    this.templateRelation.sendTo = [];
    this.defaultTemplateCount=0;
    this.priorityCounter = 0;
    this.expressionInput = [];
    this.tagsForEventExpressionBuild = [];

    //load events based on channel selection
    this.service.getRemainingEvent(this.eventConfigurationModel.channelAlias)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.events = res;
      });
   
  }

  /**
   * On change Configuration Type combo
   */
  onChangeConfigType() {
    if (this.templateRelation.type == this.config.DefaultAlias) {
      this.templateRelation.conditionExpression = '';
      this.expressionInput = [];
      this.expressionChanged = false;
    }else if(this.templateRelation.type == this.config.expressionBasedAlias){
      this.expressionChanged = true;
    }
  }


  upArrow(templateRelation){
    if(templateRelation.priority > 1){
      this.swapTemplateData(templateRelation.priority);
    }
  }

  downArrow(templateRelation){
    if(templateRelation.priority < this.eventConfigurationModel.templateRelation.length){
      this.swapTemplateData(templateRelation.priority+1);
    }
  }

  swapTemplateData(priorityToMoveUp){
    let tempDataToMoveUp = this.eventConfigurationModel.templateRelation[priorityToMoveUp-1];
    let tempDataToMoveDown = this.eventConfigurationModel.templateRelation[priorityToMoveUp-2];

    tempDataToMoveUp.priority = priorityToMoveUp-1;
    tempDataToMoveDown.priority = priorityToMoveUp;
    
    this.eventConfigurationModel.templateRelation[priorityToMoveUp-1] = tempDataToMoveDown;
    this.eventConfigurationModel.templateRelation[priorityToMoveUp-2] = tempDataToMoveUp;
  }
  
  
  removeTemplate(templateRelation, i){
    
    this.eventConfigurationModel.templateRelation = this.eventConfigurationModel.templateRelation.filter(ele =>  ele.priority !== templateRelation.priority );
    
    for (let index = 0; index < this.eventConfigurationModel.templateRelation.length; index++) {
      if(this.eventConfigurationModel.templateRelation[index].priority > templateRelation.priority){
        this.eventConfigurationModel.templateRelation[index].priority = 
        this.eventConfigurationModel.templateRelation[index].priority-1;
      }
    }

    if(this.priorityCounter>1){
      this.priorityCounter--;  
    }else{
      this.priorityCounter = 0;
    }

    this.expressionList.splice(i,1);
    if(templateRelation.type == this.config.DefaultAlias){
      this.defaultTemplateCount--;
    }

    //add template to combo
    let name = { "id": templateRelation.name,"name": templateRelation.name};
    let tempArr =[];
    if( this.templates && this.templates.length > 0) {
      this.templates.forEach(ele => tempArr.push(ele));
    }
    tempArr.push(name);
    
    this.templates = tempArr;
  }

  onSubmit(isValid: boolean) {
    // check form validation
    if (!isValid) {
      return;
    }
    
    this.service.createEventTemplateConfiguration(this.eventConfigurationModel)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        if (!res['err_msg']) {
          this.router.navigate(['notification/search_event_configuration']);
        }
      });
  }


  ngOnDestroy() {
    this.manageDestroy();
  }
}

