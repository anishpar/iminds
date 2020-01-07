import { Characteristics } from './characteristics.model';


export class TemplateRelation {
  public type= '';  //DEFAULT, EXPRESSIONBASED
  public name= '';
  public priority= 0;
  public conditionExpression= ''; // Condition
  public sendTo: Characteristics[];
}
