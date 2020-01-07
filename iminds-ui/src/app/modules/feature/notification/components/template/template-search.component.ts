import { Component, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { NotificationService } from '../../services/notification.service';
import { TemplateModel } from '../../models/template.model';
import { takeUntil } from 'rxjs/operators';
import { PaginationConfig } from 'src/app/modules/core/util/configuration/pagination.config';


@Component({
    selector: 'stl-template-search',
    templateUrl: './template-search.component.html'
  })

export class TemplateSearchComponent extends MasterComponent{

  configurationData = ['events', 'channels'];
    templateModel = new TemplateModel();
    channels = [];
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
      this.templateModel.status = this.lang.all;
      this.service.loadConfiguration(this.configurationData)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.channels = res['channels'];
        this.templateModel.channelAlias = '';
        this.events = res['events'];
        this.templateModel.eventAlias = '';
      });
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
      
      this.service.searchTemplate(this.templateModel)
        .pipe(takeUntil(this.onDestroy$))
        .subscribe(res => {
          this.searched = true;
  
          if (this.templateList) {
            this.totalItems = res.length;
            this.templateList = res;    
          }
        });
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