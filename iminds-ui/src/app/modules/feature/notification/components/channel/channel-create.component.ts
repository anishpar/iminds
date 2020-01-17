import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { NotificationService } from '../../services/notification.service';
import { TemplateModel } from '../../models/template.model';
import { Location } from '../../models/location.model';
import { Job } from '../../models/job.model';
import { Title } from '../../models/title.model';
import { JobRequest } from '../../models/job.request.model';
import { takeUntil } from 'rxjs/operators';
import { PaginationConfig } from 'src/app/modules/core/util/configuration/pagination.config';


@Component({
    selector: 'stl-channel-create.component',
    templateUrl: './channel-create.component.html'
})

export class ChannelCreateComponent extends MasterComponent implements OnInit, OnDestroy {

    configurationData = ['events', 'channels'];
      templateModel = new TemplateModel();
      location = new Location();
      channels = [];
  
      jobs = [];
      job = new Job();
  
      title = new Title();
      titles = [];
  
      jobRequest = new JobRequest();
      locations = [];
      pagination;
      totalItems;
      searched = false;
      templateList = [];
      pageOfItems: Array<any>;
      loc = '';
      
      constructor(public service: NotificationService) {
        super();
        this.pagination = Object.assign({}, PaginationConfig);
      }
      
      ngOnInit() {
        this.location.name = 'India';
        this.location.alias = 'India';
        this.locations.push(this.location);
        this.location = new Location();
        this.location.name = 'USA';
        this.location.alias = 'USA';
        this.locations.push(this.location);
        this.location = new Location();
        this.location.name = 'UK';
        this.location.alias = 'UK';
        this.locations.push(this.location);
        console.log('evetns :'+this.locations);
  
        this.title.name = 'Sr. Software Engineer';
        this.title.alias = 'Sr. Software Engineer';
        this.titles.push(this.title);
        this.title = new Title();
        this.title.name = 'Software Engineer';
        this.title.alias = 'Software Engineer';
        this.titles.push(this.title);
        this.title = new Title();
        this.title.name = 'Jr. Software Engineer';
        this.title.alias = 'Jr. Software Engineer';
        this.titles.push(this.title);
        console.log('evetns :'+this.titles);
    
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
        
        this.service.searchJob(this.jobRequest)
          .pipe(takeUntil(this.onDestroy$))
          .subscribe(res => {
            this.searched = true;
    
            if (this.jobs) {
              this.totalItems = res.length;
              this.jobs = res;    
            }
          });
      }   
  
      resetSearch() {
        this.jobRequest.location = '';
        this.jobRequest.title = '';
        this.searched = false;
        this.jobs = [];
      }
    }