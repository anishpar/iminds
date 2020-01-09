import {Component, OnInit, OnDestroy, ViewChild, ViewContainerRef} from '@angular/core';
import {MasterComponent} from 'src/app/modules/core/common/components/master.component';
import {takeUntil} from 'rxjs/operators';
import {TemplateModel} from '../../models/template.model';
import {NotificationService} from '../../services/notification.service';
import {CKEditor4} from 'ckeditor4-angular/ckeditor';
import {TemplateDetailModel} from '../../models/template-detail.model';
import {TimeRangeModel} from '../../../shared/models/time-range.model';
import { EventTags } from '../../models/tags.model';
import { EventConfiguration } from '../../models/event-configuration.model';
import { Router } from '@angular/router';

@Component({
  selector: 'stl-template-create',
  templateUrl: './template-create.component.html'
})
export class TemplateCreateComponent extends MasterComponent
  implements OnInit, OnDestroy {


 // @ViewChild('dynamicContainer' , { read: ViewContainerRef }) container: ViewContainerRef;
  configurationData = ['languages', 'channels', 'events', 'images'];
  templateModel = new TemplateModel();
  templateContent = new Array();
  channels = [];
  events = [];
  languages = [];
  images = [];
  invalidContent = [];
  maxContent = [];
  messageTag = [];
  rowId: number;
  status = true;
  eventTagsAPIResponse = [];
  eventTag ;
  listEventTags = [];
  imageRefId = [];
  eventConfigurationModel = new EventConfiguration();
  public defaultParamNameList = [];
  eventTagModel = [];
  editor = [];
  skills: any;

  constructor(public service: NotificationService, private router: Router) {
    super();
    this.eventConfigurationModel.templateRelation = [];
  }
  ngOnInit() {
    this.service.loadConfiguration(this.configurationData)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.channels = res['channels'];
        this.templateModel.channelAlias = '';
        this.events = res['events'];
        this.templateModel.eventAlias = '';
        this.languages = res['languages'];
        this.images = res['images'];
        this.resetTemplateContent();
      });
    this.templateModel.dateRange = new TimeRangeModel();
  }

  onSubmit(isValid: boolean) {
    // check form validation
    if (!isValid) {
      return;
    }
    const emptyContent = this.templateContent.find(a => a.selected && (a.content === null || a.content === ''));
    if (emptyContent !== undefined && emptyContent !== null) {
      // set focus on empty content
      this.editor[emptyContent.language].focus();
      return;
    }
    const bigContent = this.templateContent.find(a => a.selected && a.content.length > this.config.maxLength.maxText);
    if (bigContent !== undefined && bigContent !== null) {
      // set focus on big content
      this.editor[bigContent.language].focus();
      return;
    }
    this.templateModel.templateContent = this.templateContent.filter(a => a.selected);
    // check all active contents
    this.templateModel.status = this.status ? 'SHOW' : 'HIDE';
    this.service.createTemplate(this.templateModel)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        // redirect to search template page
        this.router.navigate(['/notification/search_template']);
      });
  }

  ngOnDestroy() {
    this.manageDestroy();
  }

  public onReady(event: CKEditor4.EventInfo, alias: string) {
    this.editor[alias] = event.editor;
  }

  public onTagSelect(alias: string) {
    this.editor[alias].focus();
    this.editor[alias].fire( 'saveSnapshot' );
    this.editor[alias].insertHtml(this.eventTagModel[alias]);
    this.editor[alias].fire( 'saveSnapshot' );
  }

  public onTagSelectSMS(i: number, alias: string) {
    this.templateContent[i].content = this.templateContent[i].content + this.eventTagModel[alias];
  }

  /* public addNewSkills() {
    let rows = document.getElementById("dataTable");
    let rowIdIndex = rows.innerHTML.indexOf("row");
    if (rowIdIndex == -1) {
      this.rowId = 1;
    }

    this.skills = ['CSharp', '.Net Framework', 'Asp.Net', 
    'Asp.Net Core', 'Angular 1.x', 'Angular 2.x', 'Web API', 'Azure', 'Javascript', 'SQL'];
    
    let comp = this.comFacResolver.resolveComponentFactory(SkillsRatingComponent);
    let dynamicComp = this.container.createComponent(comp);
    dynamicComp.instance.reference = dynamicComp;

    dynamicComp.instance.skills = this.skills;
    dynamicComp.instance.index = this.rowId;

    dynamicComp.instance.selectedSkill = '';
    dynamicComp.instance.yearsOfExperiences = '0';
    dynamicComp.instance.selectedRating = this.ratings[0];

    this.rowId += 1;

    let com = this.container;
    if (com !== undefined) {
      this.embeddedViews = com['_embeddedViews'].length;
    }
  } */

  public onImageSelect(alias: string) {
    this.editor[alias].focus();
    this.editor[alias].fire( 'saveSnapshot' );
    this.editor[alias].insertHtml('<img src=\'' + this.imageRefId[alias] + '\'/>');
    this.editor[alias].fire( 'saveSnapshot' );
  }

  public resetTemplateContent() {
    this.templateContent = [];
    for (let i = 0; i < this.languages.length; i++) {
      const alias = this.languages[i].alias;
      this.templateContent[i] = new TemplateDetailModel();
      this.templateContent[i].language = alias;
      this.templateContent[i].content = '';
      this.messageTag[alias] = '';
      this.invalidContent[alias] = false;
      this.maxContent[alias] = false;
      this.eventTagModel[alias] = new EventTags();
      this.imageRefId[alias] = '';
    }
    this.templateContent[0].selected = true;
    this.defaultParamNameList = [];
  }


  public onChannelChange() {
    if (this.templateModel.channelAlias === 'SMS') {
      this.templateContent = new Array();
      for (let i = 0; i < this.languages.length; i++) {
        const alias = this.languages[i].alias;
        this.templateContent[i] = new TemplateDetailModel();
        this.templateContent[i].language = alias;
        this.templateContent[i].content = '';
        this.messageTag[alias] = '';
        this.invalidContent[alias] = false;
        this.maxContent[alias] = false;
        this.eventTagModel[alias] = new EventTags();
        this.imageRefId[alias] = '';
      }
      this.templateContent[0].selected = true;
    } else {
      for (let i = 0; i < this.languages.length; i++) {
        const alias = this.languages[i].alias;
        this.messageTag[alias] = '';
        this.eventTagModel[alias] = new EventTags();
        this.imageRefId[alias] = '';
      }
      this.templateContent[0].selected = true;
    }
  }

  public onEventChange() {
    const defaultParamNameList = [];
    this.templateContent = new Array();
      for (let i = 0; i < this.languages.length; i++) {
        const alias = this.languages[i].alias;
        this.templateContent[i] = new TemplateDetailModel();
        this.templateContent[i].language = alias;
        this.templateContent[i].content = '';
        this.messageTag[alias] = '';
        this.invalidContent[alias] = false;
        this.maxContent[alias] = false;
        this.eventTagModel[alias] = new EventTags();
        this.imageRefId[alias] = '';
      }
      this.templateContent[0].selected = true;
      this.service.getEventTags('?eventAlias=' + this.templateModel.eventAlias)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.eventTagsAPIResponse = res;
        if (this.eventTagsAPIResponse != null) {
          for (let index = 0; index < this.eventTagsAPIResponse.length; index++) {
            this.eventTag = new EventTags();
            this.eventTag.id = '${' + this.eventTagsAPIResponse[index].tagAlias + '}';
            this.eventTag.text = this.eventTagsAPIResponse[index].tagName;
            defaultParamNameList.push(this.eventTag);
          }
          this.defaultParamNameList = defaultParamNameList;
        } else {
          this.defaultParamNameList = [];
        }
      });
  }

  public onBlurCkEditor(i: number, alias: string) {
    this.invalidContent[alias] = false;
    const val = this.templateContent[i].content;
    if (val === '' || val == null || val.toString().trim() === '') {
      this.invalidContent[alias] = true;
    }
  }
  public onDataChanged(i: number, alias: string) {
    this.invalidContent[alias] = false;
    this.maxContent[alias] = false;
    const val = this.templateContent[i].content;
    if (val === '' || val == null || val.toString().trim() === '') {
      this.invalidContent[alias] = true;
    } else if (val.length > this.config.maxLength.maxText) {
      this.maxContent[alias] = true;
    }
  }
}
