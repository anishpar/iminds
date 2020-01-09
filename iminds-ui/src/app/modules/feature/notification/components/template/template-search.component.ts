import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { NotificationService } from '../../services/notification.service';
import { TemplateModel } from '../../models/template.model';
import { Location } from '../../models/location.model';
import { Job } from '../../models/job.model';
import { JobRequest } from '../../models/job.request.model';
import { takeUntil } from 'rxjs/operators';
import { PaginationConfig } from 'src/app/modules/core/util/configuration/pagination.config';


@Component({
    selector: 'stl-template-search',
    templateUrl: './template-search.component.html'
  })

export class TemplateSearchComponent extends MasterComponent{

  configurationData = ['events', 'channels'];
    templateModel = new TemplateModel();
    location = new Location();
    channels = [];
    jobs = [];
    job = new Job();
    jobRequest = new JobRequest();
    events = [];
    pagination;
    totalItems;
    searched = false;
    templateList = [];
    pageOfItems: Array<any>;
    
    constructor(public service: NotificationService) {
      super();
      this.pagination = Object.assign({}, PaginationConfig);
    }
    
    ngOnInit() {
      this.location.name = 'India';
      this.location.alias = 'India';
      this.location.eventalias = 'India';
      this.events.push(this.location);
      console.log('evetns :'+this.events);
    }

    onChangePage(pageOfItems: Array<any>) {
      // update current page of items
      this.pageOfItems = pageOfItems;
    }

    ngOnDestroy() {
      this.manageDestroy();
    }

    onSubmit(isValid: boolean) {
      this.totalItems = 0;
      this.pagination = Object.assign({}, PaginationConfig);
      this.templateList = [];
      this.jobs = [];
     
      this.job.candidateCount = 2;
      this.job.hiringLead = 'Anish Parekh';
      this.job.status = 'Open';
      this.job.jobOpening ='Software Engineer';
      this.jobs.push(this.job);
      this.searched = true;
      this.totalItems = this.jobs.length;
     
      
      /*this.service.searchJob(this.jobRequest)
        .pipe(takeUntil(this.onDestroy$))
        .subscribe(res => {
          this.searched = true;
  
          if (this.jobs) {
            this.totalItems = res.length;
            this.jobs = res;    
          }
        });*/
    }   

    resetSearch() {
      this.templateModel.name = '';
      this.templateModel.eventAlias = '';
      this.templateModel.channelAlias = '';
      this.templateModel.status = this.lang.all;
      this.searched = false;
      this.templateList = [];
    }
  }