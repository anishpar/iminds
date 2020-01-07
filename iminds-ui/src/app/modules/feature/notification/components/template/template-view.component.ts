import {Component, OnInit, OnDestroy} from '@angular/core';
import {MasterComponent} from 'src/app/modules/core/common/components/master.component';
import {takeUntil} from 'rxjs/operators';
import {TemplateModel} from '../../models/template.model';
import {NotificationService} from '../../services/notification.service';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'stl-template-view',
  templateUrl: './template-view.component.html'
})
export class TemplateViewComponent extends MasterComponent
  implements OnInit, OnDestroy {
    configurationData = ['languages'];
  templateModel = new TemplateModel();
  templateData=[];
  languageList=[];
  content;
  constructor(public service: NotificationService,private router: Router ,private sanitizer: DomSanitizer) {
    super();
  }

  ngOnInit() {  
    this.service.loadConfiguration(this.configurationData,)
    .pipe(takeUntil(this.onDestroy$))
    .subscribe(res => {   
      this.languageList = res['languages']; 
        let templateName = this.getNavParam('templateName');
        if(templateName==null){
          this.router.navigate(['notification/search_template'])
        }else{
          this.viewTemplate(templateName);
          if (this.templateModel == null) {
            this.router.navigate(['notification/search_template'])
          } 
        } 
      });
                            
  }

  viewTemplate(templateName:String){
      
      this.service.viewTemplate(templateName)
        .pipe(takeUntil(this.onDestroy$))
        .subscribe(res => {            
          this.templateData = res;    
          this.templateModel = this.templateData[0];  
            let templateContentlength=this.templateData[0].templateContent.length;
            let langListLength=this.languageList.length;
            for(let j=0;j<templateContentlength;j++){
              this.content=this.templateData[0].templateContent[j].content;              
              if(this.content !=null){        
                this.templateData[0].templateContent[j].content=  this.sanitizer.bypassSecurityTrustHtml(this.content);             
          }else{
            this.templateData[0].templateContent[j].content = "-";
          }
          let lang=this.templateData[0].templateContent[j].language;             
              if(lang !=null){            
                for(let k=0;k<langListLength;k++){
                  if(lang===this.languageList[k].alias){
                  this.templateData[0].templateContent[j].language= this.languageList[k].name;
                  break;
              }
            }            
         }else{
            this.templateData[0].templateContent[j].language = "-";
          }                   
        }
      });        
  }
  ngOnDestroy() {
    this.manageDestroy();
  } 
}
