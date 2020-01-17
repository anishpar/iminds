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
import {JobSkillRel}  from '../../models/jobskillrel.model';
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


  constructor(public service: NotificationService, private router: Router) {
    super();
    this.eventConfigurationModel.templateRelation = [];
  }
  ngOnInit() {
    this.jobstatus.name = 'Draft';
    this.jobstatuses.push(this.jobstatus);
    this.jobstatus = new JobStatus();
    this.jobstatus.name = 'Open';
    this.jobstatuses.push(this.jobstatus);
    this.jobstatus = new JobStatus();
    this.jobstatus.name = 'OnHold';
    this.jobstatuses.push(this.jobstatus);
    this.jobstatus = new JobStatus();
    this.jobstatus.name = 'Filled';
    this.jobstatuses.push(this.jobstatus);
    this.jobstatus = new JobStatus();
    this.jobstatus.name = 'Canceled';
    this.jobstatuses.push(this.jobstatus);
    

    this.hiringLead.name = 'Maulik Shah';
    this.hiringLeads.push(this.hiringLead);
    this.hiringLead = new HiringLead();
    this.hiringLead.name = 'Sanjay Madhu';
    this.hiringLeads.push(this.hiringLead);
    this.hiringLead = new HiringLead();
    this.hiringLead.name = 'Pankti Joshipura';
    this.hiringLeads.push(this.hiringLead);
    this.hiringLead = new HiringLead();
    this.hiringLead.name = 'Ajay Iyer';
    this.hiringLeads.push(this.hiringLead);

    this.department.name = 'SU';
    this.departments.push(this.department);
    this.department = new Department();
    this.department.name = 'DU';
    this.departments.push(this.department);
    this.department = new Department();
    this.department.name = 'HR';
    this.departments.push(this.department);
    this.department = new Department();
    this.department.name = 'Admin';
    this.departments.push(this.department);
    this.department = new Department();
    this.department.name = 'Finance';
    this.departments.push(this.department);
    this.department = new Department();
    this.department.name = 'TU';
    this.departments.push(this.department);


    this.employeeType.name = 'Full Time';
    this.employeeTypes.push(this.employeeType);
    this.employeeType = new Department();
    this.employeeType.name = 'Part Time';
    this.employeeTypes.push(this.employeeType);
    this.employeeType = new Department();
    this.employeeType.name = 'Intern';
    this.employeeTypes.push(this.employeeType);
    this.employeeType = new Department();
    this.employeeType.name = 'Contractor';
    this.employeeTypes.push(this.employeeType);

    this.location.name = 'Ahmedabad';
    this.locations.push(this.location);
    this.location = new Location();
    this.location.name = 'Pune';
    this.locations.push(this.location);
    this.location = new Location();
    this.location.name = 'Delhi';
    this.locations.push(this.location);
    this.location = new Location();
    this.location.name = 'London';
    this.locations.push(this.location);

    this.skillType.name = 'MinimumSkill';
    this.skillTypes.push(this.skillType);
    this.skillType = new SkillType();
    this.skillType.name = 'PreferredSkill';
    this.skillTypes.push(this.skillType);

    this.interviewType.name = 'Audio';
    this.interviewTypes.push(this.interviewType);
    this.interviewType = new InterviewType();
    this.interviewType.name = 'Video';
    this.interviewTypes.push(this.interviewType);
    this.interviewType = new InterviewType();
    this.interviewType.name = 'Screening';
    this.interviewTypes.push(this.interviewType);
    this.interviewType = new InterviewType();
    this.interviewType.name = 'Aptitude';
    this.interviewTypes.push(this.interviewType);
  }

 
  onSubmit() {
    
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

