import { Component, OnDestroy, OnInit,  } from '@angular/core';
import { MasterComponent } from '../../../core/common/components/master.component';
import { StaffService } from '../services/staff.service';
import { takeUntil } from 'rxjs/operators';



@Component({
  selector: 'stl-work-list',
  templateUrl: './work-list.component.html'
})
export class WorkListComponent extends MasterComponent implements OnInit, OnDestroy {
  showOrderType = 'MY';
  loadGroup = false;
  public paymentDateShow;
  model = { email: ''};


  public defaultParamNameList = [{id:'Attr1', text: 'Attr1'}, 
  {id:'Attr2', text: 'Attr2'}, 
  {id:'Attr3', text: 'Attr3'}];

public defaultOperatorList = [ {id:'Begins With', text: 'Begins With'}, 
{id:'Contains', text: 'Contains'},
{id:'Does not Begin With', text: 'Does not Begin With'},
{id:'Does Not Contains', text: 'Does Not Contains'},
{id:'Does Not Equal', text: 'Does Not Equal'},
];

public defaultOptionValue = [{id:'1', text: 'val1'}, 
  {id:'2', text: 'val2'}, 
  {id:'3', text: 'val3'}];

public defaultJoinExpList = [
  {id:'AND', text: 'AND'}
];

  

  instituteList = [
    { id: 1, text: "name" },
    { id: 11, text: "id" },
    { id: 2, text: "email" },
    { id: 31, text: "salary" },
    { id: 3, text: "username" },
  ];
  expression;
  bankName;
  institute;

  customerAccNoDisplay = 'ELITE002321';
  constructor(public service: StaffService) {
    super();
  }

  ngOnInit() {
    
    let id = this.getNavParam('template');
    if (id === false) {
      // redirect to search page
    }
    console.log(id);
    
    /* this.service.getSampleData(id).pipe(takeUntil(this.onDestroy$)).subscribe(res => {
      console.log(res);
    }); */
    

this.lang['customer'] = {
  'custNumber': 'Customer no',
  'userName': 'Username',
  'lco': 'Partner Name',
  'name': 'Name'

}

  }

  onSubmit(isValid) {
    // check form validation
    if (!isValid) {
      return;
    }

    
    this.service.createSampleData(this.model, '123').pipe(takeUntil(this.onDestroy$)).subscribe(res => {

      // 
    });

  }

  ngOnDestroy() {
    this.manageDestroy();
  }




  selectionDone3(e) {
    this.paymentDateShow = e;
  }

}
