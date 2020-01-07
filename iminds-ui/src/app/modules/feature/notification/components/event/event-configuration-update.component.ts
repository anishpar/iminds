import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { Router, ActivatedRoute } from "@angular/router"
import { NotificationService } from '../../services/notification.service';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';
import { TemplateRelation } from '../../models/event-configuration-template-relation.model';
import { EventConfiguration } from '../../models/event-configuration.model';
import { EventTags } from '../../models/tags.model';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'stl-event-configuration-update',
  templateUrl: './event-configuration-update.component.html'
})

export class EventConfigurationUpdateComponent extends MasterComponent
  implements OnInit, OnDestroy {

  public eventDataList: any;
  configurationData = ['RECEIPIENT_TYPE'];
  templateFilter = '';
  eventConfigurationModel = new EventConfiguration();
  templateRelation = new TemplateRelation();
  editTemplateRelation = new TemplateRelation();
  eventTagsAPIResponse = [];
  eventTag = new EventTags();
  prioty = 0; //priority counter for templates
  defaultTemplateCount;
  channels = [];
  events = [];
  templates = [];
  receipientType = [];
  receipientAddress = [];
  sendToInput = '';
  expressionInput = [];
  public defaultParamNameList = [];
  enableEdit = true;
  eventAlias: String;
  channelAlias: String;
  sendToSelected = [];
  sendToList = [];
  sendToPredefinedAlias = [];

  constructor(public service: NotificationService, private dataService: DataStoreService, private route: ActivatedRoute, private router: Router) {
    super();
  }

  ngOnInit() {
    this.defaultTemplateCount = 0; //Maximum one default template is allowed in configuration
    this.service.loadConfiguration(this.configurationData)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.receipientType = res['RECEIPIENT_TYPE'];
        let sendToList = [];
        this.templateRelation.name = '';
        this.sendToPredefinedAlias = [];
        if (this.receipientType) {
          this.receipientType.forEach(ele => {
            sendToList.push({ 'id': ele.alias, 'text': ele.name })
            this.sendToPredefinedAlias.push(ele.alias);
          });
        }
        this.sendToList = sendToList;

      });


    let cacheKey = this.route.snapshot.params['channelAlias'];
    let eventList = this.dataService.getData(cacheKey);
    if (eventList != null) {
      this.eventConfigurationModel = eventList;
      this.getTemplateList();
      this.getEventTags();
      this.eventAlias = this.eventConfigurationModel.eventAlias;
      this.channelAlias = this.eventConfigurationModel.channelAlias;
      let eventChannelName: String
      eventChannelName = this.dataService.getData(cacheKey + '##');
      if (eventChannelName != null && eventChannelName != '') {
        let split = eventChannelName.split('##');
        if (split[0] != '') {
          this.channelAlias = split[0]
        }

        if (split[1] != '') {
          this.eventAlias = split[1]
        }
      }
    } else {
      this.router.navigate(['notification/search_event_configuration']);
    }

  }

  updateEditedTemplate() {
    this.enableEdit = true;
    if (this.templateRelation.type == 'DEFAULT') {
      this.defaultTemplateCount = 1;
      this.templateRelation.conditionExpression = '';
    } else {

      //   this.expressionInput.forEach(s => { expression += s.text + " " });
    }
    this.templateRelation.sendTo = [];
    this.prepareCharacteristics();
    this.eventConfigurationModel.templateRelation.push(this.templateRelation);
    this.templates = this.templates.filter(ele => ele.name !== this.templateRelation.name);
    this.resetTemplateRelation();


  }
  resetTemplateRelation() {
    this.templateRelation = new TemplateRelation();
    this.templateRelation.sendTo = [];
    this.receipientAddress = [];
    this.sendToSelected = [];
    this.enableEdit = true;

    this.eventConfigurationModel.templateRelation.sort((a, b) => a.priority - b.priority);
  }
  addTemplate() {

    //set priority for added template
    this.templateRelation.priority = ++this.prioty;

    //prepare expression
    let expression = '';
    this.expressionInput.forEach(s => { expression += s.text + " " });
    this.templateRelation.conditionExpression = expression;

    //prepare sendTo Data
    this.templateRelation.sendTo = [];
    this.prepareCharacteristics();

    this.eventConfigurationModel.templateRelation.push(this.templateRelation);
    if (this.templateRelation.type == 'DEFAULT') {
      this.defaultTemplateCount = 1;
    }
    //remove added template from combo list
    this.templates = this.templates.filter(ele => ele.name !== this.templateRelation.name);

    this.resetTemplateRelation();
  }

  upArrow(templateRelation) {
    if (templateRelation.priority > 1) {
      this.swapTemplateData(templateRelation.priority);
    }
  }

  downArrow(templateRelation) {
    if (templateRelation.priority < this.eventConfigurationModel.templateRelation.length) {
      this.swapTemplateData(templateRelation.priority + 1);
    }
  }

  swapTemplateData(priorityToMoveUp) {
    let tempDataToMoveUp = this.eventConfigurationModel.templateRelation[priorityToMoveUp - 1];
    let tempDataToMoveDown = this.eventConfigurationModel.templateRelation[priorityToMoveUp - 2];

    tempDataToMoveUp.priority = priorityToMoveUp - 1;
    tempDataToMoveDown.priority = priorityToMoveUp;

    this.eventConfigurationModel.templateRelation[priorityToMoveUp - 1] = tempDataToMoveDown;
    this.eventConfigurationModel.templateRelation[priorityToMoveUp - 2] = tempDataToMoveUp;
  }

  prepareCharacteristics() {
    if (this.sendToSelected) {
      this.sendToSelected.forEach(ele => {
        let type = this.sendToPredefinedAlias.indexOf(ele.id) === -1 ? 'RECIPIENTADDRESS' : 'RECIPIENTTYPE';
        this.templateRelation.sendTo.push({ 'name': ele.id, 'type': type });
      });
    }
  }
  cancelTemplateAction() {
    if (!this.enableEdit) {
      this.templateRelation = this.editTemplateRelation;
      this.sendToSelected = this.editTemplateRelation.sendTo;
      this.templates = this.templates.filter(ele => ele.name !== this.editTemplateRelation.name);
      this.templateRelation.sendTo = [];
      this.prepareCharacteristics();
      this.eventConfigurationModel.templateRelation.push(this.templateRelation);
    }
    this.resetTemplateRelation();

  }
  getTemplateList() {

    //filter template based on channel and event
    if (this.eventConfigurationModel != null && this.eventConfigurationModel.eventAlias != '' && this.eventConfigurationModel.channelAlias != '') {
      this.templateFilter = '?eventAlias=' + this.eventConfigurationModel.eventAlias + '&channelAlias=' + this.eventConfigurationModel.channelAlias
        + '&status=SHOW';

      let templateList = this.eventConfigurationModel.templateRelation;
      this.service.getTemplatesByFilter(this.templateFilter)
        .pipe(takeUntil(this.onDestroy$))
        .subscribe(res => {
          let templates = res;
          let priority = 0;
          if (templateList != null && templateList.length > 0) {
            for (let i = 0; i < templateList.length; i++) {
              if (templateList[i].type == 'DEFAULT') {
                this.defaultTemplateCount = 1;
              }
              if (templateList[i].priority > priority) {
                priority = templateList[i].priority;
              }
              templates = templates.filter(ele => ele.name != templateList[i].name);
            }

          }
          this.prioty = priority;
          this.templates = templates;
        });
    }
  }

  getEventTags() {
    this.service.getEventTags('?eventAlias=' + this.eventConfigurationModel.eventAlias)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {

        if (res && res.length > 0) {
          this.eventTagsAPIResponse = res;
          for (let index = 0; index < this.eventTagsAPIResponse.length; index++) {
            this.eventTag.id = this.eventTagsAPIResponse[index].tagAlias;
            this.eventTag.text = this.eventTagsAPIResponse[index].tagAlias;
            this.defaultParamNameList.push(this.eventTag);
            this.eventTag = new EventTags();
          }
        }
      });
  }

  editTemplate(templateRelation: any) {
    this.editTemplateRelation = new TemplateRelation();
    this.enableEdit = false;
    let name = { "id": templateRelation.name, "name": templateRelation.name };
    let tempArr = [];
    if (this.templates && this.templates.length > 0) {
     
      this.templates.forEach(ele => {
        
          tempArr.push(ele);
       });
    }
    tempArr.push(name);

    this.templates = tempArr;

    this.templateRelation.name = templateRelation.name;
    this.editTemplateRelation.name = templateRelation.name
    this.templateRelation.priority = templateRelation.priority;
    this.editTemplateRelation.priority = templateRelation.priority
    this.templateRelation.type = templateRelation.type;
    this.editTemplateRelation.type = templateRelation.type

    let sendToEditList = [];
    if (templateRelation.sendTo != null && templateRelation.sendTo.length > 0 && templateRelation.sendTo.name != '') {
      templateRelation.sendTo.forEach(ele => {
        if (ele.name != '') {
          sendToEditList.push({ 'id': ele.name, 'text': ele.name });
        }
      });
      this.sendToSelected = sendToEditList;
    }
    this.editTemplateRelation.sendTo = sendToEditList;
    if (templateRelation.type == 'DEFAULT') {
      this.defaultTemplateCount = 0;
      this.templateRelation.conditionExpression = '';
      this.editTemplateRelation.conditionExpression = '';
      this.editTemplateRelation.sendTo = sendToEditList;
    } else {
      this.templateRelation.conditionExpression = templateRelation.conditionExpression;
      this.editTemplateRelation.conditionExpression = templateRelation.conditionExpression;
      this.expressionInput = templateRelation.conditionExpression;
    }
    this.eventConfigurationModel.templateRelation = this.eventConfigurationModel.templateRelation.filter(ele => ele.name != templateRelation.name);

  }
  /**
   * On change Configuration Type combo
   */
  onChangeConfigType() {
    if (this.templateRelation.type == 'DEFAULT') {
      this.templateRelation.conditionExpression = '';
    }
  }


  removeTemplate(templateRelation: any) {
    this.eventConfigurationModel.templateRelation = this.eventConfigurationModel.templateRelation.filter(ele => ele.priority !== templateRelation.priority);
    if (this.eventConfigurationModel.templateRelation != null && this.eventConfigurationModel.templateRelation.length > 0) {
      let templateList = this.eventConfigurationModel.templateRelation;
      let size = templateList.length;
      for (let i = 0; i < size; i++) {
        let priority = i + 1;
        if (templateList[i].priority != priority) {
          templateList[i].priority = priority;
        }
      }
      this.eventConfigurationModel.templateRelation = templateList;
    }
    this.prioty--;
    if (templateRelation.type == 'DEFAULT') {
      this.defaultTemplateCount = 0;
    }

    //add template to combo
    let name = { "id": templateRelation.name, "name": templateRelation.name };
    let tempArr = [];
    if (this.templates && this.templates.length > 0) {
      this.templates.forEach(ele => {
          tempArr.push(ele);
      });
    }
    tempArr.push(name);

    this.templates = tempArr;
  }

  onSubmit(isValid: boolean) {
    // check form validation
    if (!isValid) {
      return;
    }
    this.service.updateEventTemplateConfiguration(this.eventConfigurationModel)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        if (!res['err_msg']) {
          //reset data
        }
      });
  }


  ngOnDestroy() {
    this.manageDestroy();
  }




}