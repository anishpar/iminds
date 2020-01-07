export interface OptionsBehavior {
  first():any;
  last():any;
  prev():any;
  next():any;
  filter(query:RegExp):any;
  filterByMode(query:RegExp, mode:number):any;
}
