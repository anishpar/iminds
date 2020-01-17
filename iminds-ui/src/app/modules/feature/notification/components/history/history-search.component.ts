import { MasterComponent } from 'src/app/modules/core/common/components/master.component';
import { OnInit, OnDestroy, Component } from '@angular/core';
import { HistoryModel } from '../../models/history.model';
import { DateRangeModel } from '../../../shared/models/date-range.model';
import { takeUntil } from 'rxjs/operators';
import {NotificationService} from '../../services/notification.service';
import {CKEditor4} from 'ckeditor4-angular/ckeditor';
import {TemplateDetailModel} from '../../models/template-detail.model';
import {TimeRangeModel} from '../../../shared/models/time-range.model';
import { EventTags } from '../../models/tags.model';
import {Department}  from '../../models/department.model';
import { Location } from '../../models/location.model';
import {JobStatus}  from '../../models/job.status.model';
import {EmployeeType}  from '../../models/employeetype.model';
import {AddJobOpening}  from '../../models/addjobopening.model';
import {HiringLead}  from '../../models/hiringlead.model';
import {SkillType}  from '../../models/skilltype.model';
import {InterviewType}  from '../../models/interviewtype.model';
import {JobQuestionsRel}  from '../../models/jobquestions.model';
import {JobInterviewRel}  from '../../models/jobinterview.model';
import {CandidateJobApply}  from '../../models/candidatejobapply.model';
import {JobSkillRel}  from '../../models/jobskillrel.model';
import {CandidateJobRel}  from '../../models/candidatejobrel.model';
import {CandidateSkillRel}  from '../../models/candidatekillrel.model';
import { PaginationConfig } from 'src/app/modules/core/util/configuration/pagination.config';
import { HistoryReceiverModel } from '../../models/history-receiver.model';
import { HistoryDetailsModel } from '../../models/history-details.model';
import {AssociateRecruiter} from '../../models/associate.recruiter.model';
import {TemplateModel} from '../../models/template.model';
import {JobOpeningStatusChange} from '../../models/jobopeningstatuschange.model';
import { EventConfiguration } from '../../models/event-configuration.model';
import { DataStoreService } from 'src/app/modules/core/util/services/data-store.service';
import { Router } from '@angular/router';

@Component({
  selector: 'stl-history-search',
  templateUrl: './history-search.component.html'
})

export class SearchHistoryComponent extends MasterComponent
  implements OnInit, OnDestroy {
    configurationData = ['languages', 'channels', 'events', 'images'];
    templateModel = new TemplateModel();
    templateContent = new Array();
    add
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
    jobstatuses = [];
    jobstatus = new JobStatus();
    associateRecruiter = new AssociateRecruiter();
    associateRecruiters = [];

    jobOpeningStatusChange = new JobOpeningStatusChange();
  
    hiringLeads = [];
    hiringLead = new HiringLead();
  
    departments = [];
    department = new Department();
  
    employeeTypes = [];
    employeeType = new EmployeeType();
  
    locations = [];
    location = new Location();
  
    skillTypes = [];
    skillType = new SkillType();
  
    interviewTypes = [];
    interviewType = new InterviewType();
    jobOpeningId = null;
  
    jobSkill1 = new JobSkillRel();
    jobSkill2 = new JobSkillRel();
    jobSkill3 = new JobSkillRel();
  
    candidateSkill1 = new CandidateSkillRel();
    candidateSkill2 = new CandidateSkillRel();
    candidateSkill3 = new CandidateSkillRel();
    candidateSkills1 = [];
    fileInfo: string;
    jobSkills = [];
  
    addJobOpening = new AddJobOpening();
  
    candidateJobApply = new CandidateJobApply();
  
    candidateJobRel = new CandidateJobRel();
    candidateJobRels = [];
  
  
    constructor(public service: NotificationService, private router: Router) {
      super();
      this.eventConfigurationModel.templateRelation = [];
    }
    ngOnInit() {

      this.associateRecruiter.name = 'Faiyaz Maru';
      this.associateRecruiters.push(this.associateRecruiter);
      this.associateRecruiter = new AssociateRecruiter();
      this.associateRecruiter.name = 'Khyati Jani';
      this.associateRecruiters.push(this.associateRecruiter);
      this.associateRecruiter = new AssociateRecruiter();
      this.associateRecruiter.name = 'Roma Patel';
      this.associateRecruiters.push(this.associateRecruiter);
      this.associateRecruiter = new AssociateRecruiter();
      this.associateRecruiter.name = 'Sreekanth Menon';
      this.associateRecruiters.push(this.associateRecruiter);
  
      let job = this.getNavParam('jobInfo');
      this.jobOpeningId = job.jobOpeningid;
      console.log("jobOpeningId :"+this.jobOpeningId);
      
      this.service.getJobDetailByJobId(this.jobOpeningId)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
          this.addJobOpening = res;
          this.jobSkill1 = this.addJobOpening.jobSkills[0];
          this.jobSkill2 = this.addJobOpening.jobSkills[1];
          this.jobSkill3 = this.addJobOpening.jobSkills[2];
        
      }); 
  
    }
  
   
    onApprove() {
      this.jobOpeningStatusChange.jobOpeningid = this.jobOpeningId;
      this.jobOpeningStatusChange.status = "APPROVED";
      console.log(this.jobOpeningStatusChange);
     
  
      this.service.changeJobStatus(this.jobOpeningStatusChange)
            .pipe(takeUntil(this.onDestroy$))
            .subscribe(res => {
                this.router.navigate(['/notification/create_channel']);
            });
    } 

    onDecline() {
      this.jobOpeningStatusChange.jobOpeningid = this.jobOpeningId;
      this.jobOpeningStatusChange.status = "DECLINED";
      console.log(this.jobOpeningStatusChange);
     
  
      this.service.changeJobStatus(this.jobOpeningStatusChange)
            .pipe(takeUntil(this.onDestroy$))
            .subscribe(res => {
                this.router.navigate(['/notification/create_channel']);
            });
    } 

  
    ngOnDestroy() {
      this.manageDestroy();
    }
  
    onFileSelect(input: HTMLInputElement): void {
  
      /**
       * Format the size to a human readable string
       *
       * @param bytes
       * @returns the formatted string e.g. `105 kB` or 25.6 MB
       */
      function formatBytes(bytes: number): string {
        const UNITS = ['Bytes', 'kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
        const factor = 1024;
        let index = 0;
  
        while (bytes >= factor) {
          bytes /= factor;
          index++;
        }
  
        return `${parseFloat(bytes.toFixed(2))} ${UNITS[index]}`;
      }
  
      const file = input.files[0];
      this.fileInfo = `${file.name} (${formatBytes(file.size)})`;
    }
  
    
  
    
  }
  
  