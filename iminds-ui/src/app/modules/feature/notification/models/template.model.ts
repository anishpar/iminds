import {TimeRangeModel} from '../../shared/models/time-range.model';
import {TemplateDetailModel} from './template-detail.model';

export class TemplateModel {
  public name: string;
  public description: string;
  public status: string; // SHOW, HIDE
  public channelAlias: string; // Notification Channel
  public eventAlias: string; // Format Type
  public dateRange: TimeRangeModel;
  public templateContent: TemplateDetailModel[];
}
