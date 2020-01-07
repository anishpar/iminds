import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';
import { TemplateCreateComponent } from './components/template/template-create.component';
import { CKEditorModule } from 'ckeditor4-angular';
import { EventConfigurationCreateComponent } from './components/event/event-configuration-create.component';
import { SearchHistoryComponent } from './components/history/history-search.component';
import { ViewHistoryComponent } from './components/history/history-view.component';
import { EventConfigurationSearchComponent} from './components/event/event-configuration-search.component';
import { ViewEventConfigurationComponent } from './components/event/event-configuration-view.component';
import { EventConfigurationUpdateComponent } from './components/event/event-configuration-update.component';
import { TemplateSearchComponent } from './components/template/template-search.component';
import { TemplateViewComponent } from './components/template/template-view.component';
import { ChannelCreateComponent } from './components/channel/channel-create.component';


const routes: Routes = [
  { path: 'create_template', component: TemplateCreateComponent },
  { path: 'search_template', component: TemplateSearchComponent },
  { path: 'create_event_configuration', component: EventConfigurationCreateComponent },
  { path: 'search_history', component: SearchHistoryComponent },
  { path: 'view_history', component: ViewHistoryComponent },
  { path: 'search_event_configuration', component: EventConfigurationSearchComponent },
  { path: 'view_event_configuration/:channelAlias', component: ViewEventConfigurationComponent },
  { path: 'update_event_configuration/:channelAlias', component: EventConfigurationUpdateComponent },
  { path: 'view_template', component: TemplateViewComponent },
  { path: 'create_channel', component: ChannelCreateComponent },


];

@NgModule({
  imports: [
    RouterModule.forChild(routes), SharedModule, CKEditorModule
  ],
  declarations: [
    TemplateSearchComponent, TemplateCreateComponent, TemplateViewComponent,
    EventConfigurationSearchComponent, EventConfigurationCreateComponent, EventConfigurationUpdateComponent,
    ViewEventConfigurationComponent,
    SearchHistoryComponent, ViewHistoryComponent,
    ChannelCreateComponent],
  providers: [],
  exports: []
})


export class NotificationModule { }

