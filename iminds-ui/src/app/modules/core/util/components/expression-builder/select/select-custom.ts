import { SelectItem } from './select-item';
import { ExpressionBuilderComponent } from '../expression-builder.component';
import { OptionsBehavior } from './select-interfaces';
import { stripTags } from 'ng2-select';


export class Behavior {
    public optionsMap: Map<string, number> = new Map<string, number>();
  
    public actor: ExpressionBuilderComponent;
  
    public constructor(actor: ExpressionBuilderComponent) {
      this.actor = actor;
    }
  
    public fillOptionsMap(): void {
      this.optionsMap.clear();
      let startPos = 0;
      this.actor.itemObjects
        .map((item: SelectItem) => {
          startPos = item.fillChildrenHash(this.optionsMap, startPos);
        });
    }
  
    public ensureHighlightVisible(optionsMap: Map<string, number> = void 0): void {
      let container = this.actor.element.nativeElement.querySelector('.ui-select-choices');
      if (!container) {
        return;
      }
      let choices = container.querySelectorAll('.ui-select-choices-row');
      if (choices.length < 1) {
        return;
      }
      let activeIndex = this.getActiveIndex(optionsMap);
      if (activeIndex < 0) {
        return;
      }
      let highlighted: any = choices[activeIndex];
      if (!highlighted) {
        return;
      }
      let posY: number = highlighted.offsetTop + highlighted.clientHeight - container.scrollTop;
      let height: number = container.offsetHeight;
      if (posY > height) {
        container.scrollTop += posY - height;
      } else if (posY < highlighted.clientHeight) {
        container.scrollTop -= highlighted.clientHeight - posY;
      }
    }
  
    private getActiveIndex(optionsMap: Map<string, number> = void 0): number {
      let ai = this.actor.options.indexOf(this.actor.activeOption);
      if (ai < 0 && optionsMap !== void 0) {
        ai = optionsMap.get(this.actor.activeOption.id);
      }
      return ai;
    }
  }
  
  export class GenericBehavior extends Behavior implements OptionsBehavior {
    public constructor(actor: ExpressionBuilderComponent) {
      super(actor);
    }
  
    public first(): void {
      this.actor.activeOption = this.actor.options[0];
      super.ensureHighlightVisible();
    }
  
    public last(): void {
      this.actor.activeOption = this.actor.options[this.actor.options.length - 1];
      super.ensureHighlightVisible();
    }
  
    public prev(): void {
      let index = this.actor.options.indexOf(this.actor.activeOption);
      this.actor.activeOption = this.actor
        .options[index - 1 < 0 ? this.actor.options.length - 1 : index - 1];
      super.ensureHighlightVisible();
    }
  
    public next(): void {
      let index = this.actor.options.indexOf(this.actor.activeOption);
      this.actor.activeOption = this.actor
        .options[index + 1 > this.actor.options.length - 1 ? 0 : index + 1];
      super.ensureHighlightVisible();
    }
  
    public filter(query: RegExp): void {
      let options = this.actor.itemObjects
        .filter((option: SelectItem) => {
          return stripTags(option.text).match(query) &&
            (this.actor.multiple === false ||
              (this.actor.multiple === true && this.actor.active.map((item: SelectItem) => item.id).indexOf(option.id) < 0));
        });
      this.actor.options = options;
      if (this.actor.options.length > 0) {
        this.actor.activeOption = this.actor.options[0];
        super.ensureHighlightVisible();
      }
    }

    public filterByMode(query: RegExp, mode = 1): void {
      let itemObjects = this.actor.itemObjects;
      switch (mode) {
        case 1:
              itemObjects = this.actor.paramObjects;
            break;
        case 2:
              itemObjects = this.actor.operatorObjects;
            break;
        case 4:
              itemObjects = this.actor.joinExpObjects;
          break;

      }
      if (mode !== 3) {

        let options = itemObjects.filter((option: SelectItem) => {
          return stripTags(option.text).match(query);
        });
        this.actor.options = options;
        if (this.actor.options && this.actor.options.length > 0) {
          this.actor.outOfOption = false;
          this.actor.activeOption = this.actor.options[0];
          super.ensureHighlightVisible();
        } else {
          this.actor.outOfOption = true;
        }
      }
    }


  }
  

  