import {Component, OnInit, OnDestroy, ViewChild, ViewContainerRef} from '@angular/core';
import {MasterComponent} from 'src/app/modules/core/common/components/master.component';
import {takeUntil} from 'rxjs/operators';
import {TemplateModel} from '../../models/template.model';
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
import {JobInterviewRel}  from '../../models/jobinterview.model';
import {JobQuestionsRel}  from '../../models/jobquestions.model';
import {CandidateJobApply}  from '../../models/candidatejobapply.model';
import {JobSkillRel}  from '../../models/jobskillrel.model';
import { EventConfiguration } from '../../models/event-configuration.model';
import { Router } from '@angular/router';

@Component({
    selector: 'stl-history-view.component',
    templateUrl: './history-view.component.html'
  })

  export class ViewHistoryComponent extends MasterComponent implements OnInit, OnDestroy{
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
  jobstatuses = [];
  jobstatus = new JobStatus();

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

  jobSkill1 = new JobSkillRel();
  jobSkill2 = new JobSkillRel();
  jobSkill3 = new JobSkillRel();

  jobSkills = [];

  addJobOpening = new AddJobOpening();

  candidateJobApply = new CandidateJobApply();


  constructor(public service: NotificationService, private router: Router) {
    super();
    this.eventConfigurationModel.templateRelation = [];
  }
  ngOnInit() {

    let job = this.getNavParam('jobOpeningId');
    let jobOpeningId = job.jobOpeningid;
    console.log("jobOpeningId :"+jobOpeningId);
    
    this.service.getJobDetailByJobId(jobOpeningId)
    .pipe(takeUntil(this.onDestroy$))
    .subscribe(res => {
        this.addJobOpening = res;
        this.jobSkill1 = this.addJobOpening.jobSkills[0];
        this.jobSkill2 = this.addJobOpening.jobSkills[1];
        this.jobSkill3 = this.addJobOpening.jobSkills[2];
      
    }); 

  }

 
  onSubmit() {
    
    console.log(this.candidateJobApply);
    this.jobSkills.push(this.jobSkill1);
    this.jobSkills.push(this.jobSkill2);
    this.jobSkills.push(this.jobSkill3);
    this.addJobOpening.jobSkills =  this.jobSkills;
    console.log(this.addJobOpening);
    this.service.addJobOpening(this.addJobOpening)
      .pipe(takeUntil(this.onDestroy$))
      .subscribe(res => {
        this.reset();
      });
  } 

  reset() {
    this.addJobOpening.title = '';
    this.addJobOpening.jobStatus = '';
    this.addJobOpening.hiringLead = '';
    this.addJobOpening.department = '';
    this.addJobOpening.employeeType = '';
    this.addJobOpening.jobDescription = '';
    this.addJobOpening.minimumExp = null;
    this.addJobOpening.location = '';
    this.addJobOpening.compensation = '';
    this.addJobOpening.jobSkills = [];
    this.addJobOpening.jobQuestionsRels = [];
    this.addJobOpening.jobInterviewRels = [];

    this.jobSkill1.minimumExp = null;
    this.jobSkill1.name = '';
    
    this.jobSkill2.minimumExp = null;
    this.jobSkill2.name = '';

    this.jobSkill3.minimumExp = null;
    this.jobSkill3.name = '';
  }

  ngOnDestroy() {
    this.manageDestroy();
  }

  

  
}

