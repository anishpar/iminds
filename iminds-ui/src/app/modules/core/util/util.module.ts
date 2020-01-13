import { NgModule, ErrorHandler } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CookieModule } from 'ngx-cookie';
import { UiSwitchModule } from 'ngx-ui-switch';

import { PaginationModule, PopoverModule, ModalModule
  , BsDatepickerModule, AccordionModule, TimepickerModule, TabsModule, TooltipModule } from 'ngx-bootstrap';
import { SelectModule } from 'ng2-select';


import { StlValidatorDirective } from './directives/stl-validator.directive';
import { ConfirmModalComponent } from './components/confirm-modal.component';
import { InputTypeaheadComponent } from './components/input-typeahead.component';
import { PaginationComponent } from './components/pagination.component';
import { InputDateComponent } from './components/input-date.component';
import { InputDateRangeComponent } from './components/input-date-range.component';
import { StlErrorHandler } from './services/stl-error.handler';
import { CrudeService } from './services/crude.service';
import { ExpressionBuilderComponent } from './components/expression-builder/expression-builder.component';
import { MultiSelectComponent } from './components/multi-select.component';
import { ChartsModule } from 'ng2-charts/ng2-charts';
@NgModule({
  declarations: [ StlValidatorDirective
     , InputTypeaheadComponent , InputTypeaheadComponent
     , ConfirmModalComponent,  PaginationComponent, InputDateComponent
     , InputDateRangeComponent, ExpressionBuilderComponent, MultiSelectComponent
    ],
  imports: [
    FormsModule, CommonModule, RouterModule, SelectModule,
    PaginationModule.forRoot() , TimepickerModule.forRoot(), PopoverModule.forRoot()
    , ModalModule.forRoot(), BsDatepickerModule.forRoot(), CookieModule.forRoot()
    , AccordionModule.forRoot(), TabsModule.forRoot()
    , TooltipModule.forRoot(), UiSwitchModule,ChartsModule
  ],
  providers: [
    {provide: ErrorHandler, useClass: StlErrorHandler, deps: [ CrudeService ]}
  ],

  exports: [ StlValidatorDirective
     , ConfirmModalComponent
     , InputTypeaheadComponent
     , PaginationComponent, InputDateComponent, InputDateRangeComponent, ExpressionBuilderComponent
     , MultiSelectComponent
     , PopoverModule, ModalModule, AccordionModule, RouterModule, CommonModule, FormsModule, TabsModule, SelectModule, TooltipModule
     , TimepickerModule, UiSwitchModule,ChartsModule
    ]
})
export class UtilModule { }
