import { Component, OnInit, forwardRef, Input, Output, ElementRef, EventEmitter } from '@angular/core';
import { MasterComponent } from '../../../common/components/master.component';
import { NG_VALUE_ACCESSOR, NG_VALIDATORS, ControlValueAccessor, FormControl } from '@angular/forms';
import { SelectItem } from './select/select-item';
import { OptionsBehavior } from './select/select-interfaces';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { escapeRegexp } from './select/common';
import { GenericBehavior } from './select/select-custom';
import { stripTags } from 'ng2-select';


@Component({
  selector: 'stl-expression-builder',
  templateUrl: './expression-builder.component.html',
  styleUrls: ['./expression-builder.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      /* tslint:disable */
      useExisting: forwardRef(() => ExpressionBuilderComponent),
      /* tslint:enable */
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => ExpressionBuilderComponent),
      multi: true,
    }
  ]

})
export class ExpressionBuilderComponent extends MasterComponent implements OnInit, ControlValueAccessor {

  mode = 1;
  /* UI SELECT CODE STARTS */

  @Input() public allowClear: boolean = false;
  @Input() public placeholder: string = '';
  @Input() public idField: string = 'id';
  @Input() public textField: string = 'text';
  public multiple: boolean = true; //set to true

  protected onChange: any = Function.prototype;
  protected onTouched: any = Function.prototype;
  
  @Input()
  public set items(value: Array<any>) {
    if (!value) {
      this._items = this.itemObjects = [];
    } else {
      this._items = value.filter((item: any) => {
        if ((typeof item === 'string') || (typeof item === 'object' && item && item[this.textField] && item[this.idField])) {
          return item;
        }
      });
      this.itemObjects = this._items.map((item: any) => (typeof item === 'string' ? new SelectItem(item) : new SelectItem({ id: item[this.idField], text: item[this.textField] })));
    }
  }


  // Custom
  public isEditMode = false;
  public editPosition;
  public outOfOption = false;
  // custom starts
  public defaultParamNameList = [{ id: 'Attr1', text: 'Attr1' },
  { id: 'Attr2', text: 'Attr2' },
  { id: 'Attr3', text: 'Attr3' }];

  public defaultOperatorList = [
  { id: 'Begins With', text: 'Begins With' },
  { id: 'Contains', text: 'Contains' },
  { id: 'Ends With', text: 'Ends With' },
  { id: 'Equals', text: 'Equals' },
  { id: 'Less Than', text: 'Less Than' },
  { id: 'Greater Than', text: 'Greater Than' },
  
  { id: 'DoesNotBeginWith', text: 'Does not Begin With' },
  { id: 'DoesNotContains', text: 'Does Not Contains' },
  { id: 'DoesNotEqual', text: 'Does Not Equal' },
  
  // { id: 'NotNull', text: 'Not Null' }
  // { id: 'IsNull', text: 'Is Null' },


  // { id: 'Greater Than Equal To', text: 'Greater Than Equal To' },
  // { id: 'Less Than Equal To', text: 'Less Than Equal To' },
  // { id: 'List', text: 'List' },
];

  public defaultJoinExpList = [{ id: 'AND', text: 'AND' },  { id: 'OR', text: 'OR' }];

  public paramObjects: Array<SelectItem> = [];
  _paramList;

  @Input()
  public set paramList(value: Array<any>) {
    if (!value) {
      this._paramList = this.paramObjects = [];
    } else {
      this._paramList = value.filter((item: any) => {
        if ((typeof item === 'string') || (typeof item === 'object' && item && item[this.textField] && item[this.idField])) {
          return item;
        }
      });
      this.paramObjects = this._paramList.map((item: any) => (typeof item === 'string' ? new SelectItem(item) : new SelectItem({ id: item[this.idField], text: item[this.textField], mode: 1 })));
    }
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
}

  public operatorObjects: Array<SelectItem> = [];
  _operatorList;

  @Input()
  public set operatorList(value: Array<any>) {
    if (!value) {
      this._operatorList = this.operatorObjects = [];
    } else {
      this._operatorList = value.filter((item: any) => {
        if ((typeof item === 'string') || (typeof item === 'object' && item && item[this.textField] && item[this.idField])) {
          return item;
        }
      });
      this.operatorObjects = this._operatorList.map((item: any) => (typeof item === 'string' ? new SelectItem(item) : new SelectItem({ id: item[this.idField], text: item[this.textField], mode: 2 })));
    }
  }


  public joinExpObjects: Array<SelectItem> = [];
  _joinExpList;

  @Input()
  public set joinExpList(value: Array<any>) {
    if (!value) {
      this._joinExpList = this.joinExpObjects = [];
    } else {
      this._joinExpList = value.filter((item: any) => {
        if ((typeof item === 'string') || (typeof item === 'object' && item && item[this.textField] && item[this.idField])) {
          return item;
        }
      });
      this.joinExpObjects = this._joinExpList.map((item: any) => (typeof item === 'string' ? new SelectItem(item) : new SelectItem({ id: item[this.idField], text: item[this.textField], mode: 4 })));
    }
  }

  
  updatePosition(i: number) {
    this.isEditMode = true;
    this.editPosition = i;
    this.mode = this.active[i].mode;
    this.open();
    this.element.nativeElement.querySelector('.ui-select-container').focus();
  }
  cancleUpdate() {
    if (this.isEditMode) {
      this.isEditMode = false;
      this.mode = this.getNextMode();
      this.editPosition = null;
      this.open();
    }
  }
  @Input()
  public set disabled(value: boolean) {
    this._disabled = value;
    if (this._disabled === true) {
      this.hideOptions();
    }
  }

  public get disabled(): boolean {
    return this._disabled;
  }

  @Input()
  public set active(selectedItems: Array<any>) {
    if (!selectedItems || selectedItems.length === 0) {
      this._active = [];
      this.mode = 1;
      // change
    } else if (Array.isArray(selectedItems)) {
      let areItemsStrings = typeof selectedItems[0] === 'string';

      this._active = selectedItems.map((item: any) => {
        let data = areItemsStrings
          ? item
          : { id: item[this.idField], text: item[this.textField] };

        return new SelectItem(data);
      });
    }
  }



  @Output() public data: EventEmitter<any> = new EventEmitter();

  public options: Array<SelectItem> = [];
  public itemObjects: Array<SelectItem> = [];
  public activeOption: SelectItem;
  public element: ElementRef;

  public get active(): Array<any> {
    return this._active;
  }

  private set optionsOpened(value: boolean) {
    this._optionsOpened = value;
  }

  private get optionsOpened(): boolean {
    return this._optionsOpened;
  }

  // protected onChange: any = Function.prototype;
  // protected onTouched: any = Function.prototype;

  private inputMode: boolean = false;
  private _optionsOpened: boolean = false;
  private behavior: OptionsBehavior;
  private inputValue: string = '';
  private _items: Array<any> = [];
  private _disabled: boolean = false;
  private _active: Array<SelectItem> = [];
  public pCount = 0;
  public expAttrCount = 0;

  public constructor(element: ElementRef, private sanitizer: DomSanitizer) {
    super();
    this.element = element;
    this.clickedOutside = this.clickedOutside.bind(this);


    if (this.operatorList == null || this.operatorList.length == 0) {
      this.operatorList = this.defaultOperatorList;
    }
    if (this.joinExpList == null || this.joinExpList.length == 0) {
      this.joinExpList = this.defaultJoinExpList;
    }
  }

  public sanitize(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  public inputEvent(e: any, isUpMode: boolean = false): void {
    // tab
    if (e.keyCode === 9) {
      return;
    }
    let target = e.target || e.srcElement;
    // console.log(e.keyCode, isUpMode, target.value, 'inputEvent');

    if (isUpMode && (e.keyCode === 37 || e.keyCode === 39 || e.keyCode === 38 ||
      e.keyCode === 40 || e.keyCode === 13)) {
      e.preventDefault();
      return;
    }
    // backspace
    if (!isUpMode && e.keyCode === 8) {
      let el: any = this.element.nativeElement
        .querySelector('div.ui-select-container > input');
      if (!el.value || el.value.length <= 0) {
        if (this.active.length > 0) {
          this.remove(this.active[this.active.length - 1]);
        }
        e.preventDefault();
      }
    }
    // esc
    if (!isUpMode && e.keyCode === 27) {
      this.hideOptions();
      this.cancleUpdate();
      this.element.nativeElement.children[0].focus();
      e.preventDefault();
      return;
    }
    // del
    if (!isUpMode && e.keyCode === 46) {
      if (this.active.length > 0) {
        this.remove(this.active[this.active.length - 1]);
      }
      e.preventDefault();
    }
    // left
    if (!isUpMode && e.keyCode === 37) {
      //this.behavior.first();
      let position, node;
      let loopBreaker = 10;
      do {
        if (typeof this.editPosition === 'undefined' || this.editPosition === null) {
          this.editPosition = position = this.active.length -1;
        } else {
          position = position ? position - 1 : this.editPosition - 1;
        }
        if (position < 0 ){
          position = this.active.length -1;
        }
        node = this.active[position];
        loopBreaker--;
        if (loopBreaker<=0) {
          break;
        }
      } while(node.text == '(' || node.text == ')');

      this.updatePosition(position);

      e.preventDefault();
      return;
    }
    // right
    if (!isUpMode && e.keyCode === 39) {
      // this.behavior.last();
      let position, node;
      let loopBreaker = 10;
      do {
        if (typeof this.editPosition === 'undefined' || this.editPosition === null) {
          this.editPosition = position = 0;
        } else {
          position = position ? position + 1 : this.editPosition + 1;
        }
        if (position >= this.active.length){
          position = 0;
        }
        node = this.active[position];
        loopBreaker--;
        if (loopBreaker<=0) {
          break;
        }
      } while(node.text == '(' || node.text == ')');

      
      this.updatePosition(position);

      e.preventDefault();
      return;
    }
    // up
    if (!isUpMode && e.keyCode === 38) {
      this.behavior.prev();
      e.preventDefault();
      return;
    }
    // down
    if (!isUpMode && e.keyCode === 40) {
      this.behavior.next();
      e.preventDefault();
      return;
    }
    
    // enter
    if (!isUpMode && e.keyCode === 13) {
      if (this.mode == 3) {

        if (target.value && target.value.trim().length > 0) {
          this.selectMatch(new SelectItem(target.value));
        }
      } else if ((target.value === ')' || target.value === '(')) {
        this.selectMatch(new SelectItem(target.value), e, false);
      } else if (!this.outOfOption) {

        // if (this.active.indexOf(this.activeOption) === -1) {
        this.selectActiveMatch();
        this.behavior.next();
        // }
      }
      e.preventDefault();
      return;
    }
    
    if (target && target.value && isUpMode) {
      // console.log(e.keyCode, isUpMode, target.value, 'target.value', isUpMode);

    
      this.inputValue = target.value;
      this.behavior.filterByMode(new RegExp(escapeRegexp(this.inputValue), 'ig'), this.mode);
      this.doEvent('typed', this.inputValue);
    
    } else {
      this.open();
    }
    e.stopPropagation();
  }

  public onKeyUp(e: any) {
    if ((e.keyCode === 37 || e.keyCode === 39 || e.keyCode === 38 ||
      e.keyCode === 40 || e.keyCode === 13)) {
      e.preventDefault();
      return;
    }
  }

  public ngOnInit(): any {
    this.behavior = new GenericBehavior(this);

  }

  public remove(item: SelectItem): void {
    if (this._disabled === true) {
      return;
    }
    if (this.multiple === true && this.active) {
      // let index = this.active.indexOf(item);
      let index = this.active.length - 1;
      let removedItem = this.active[index];
      this.active.splice(index, 1);
      this.data.next(this.active);
      this.doEvent('removed', item);
      // chanage mode
      this.mode = removedItem.mode;

    }
  }

  public doEvent(type: string, value: any): void {
    if ((this as any)[type] && value) {
      (this as any)[type].next(value);
    }

    // this.onTouched();
    if (type === 'selected' || type === 'removed') {
      this.onChange(this.active);
    }
  }

  public clickedOutside(): void {
    this.inputMode = false;
    this.optionsOpened = false;
  }

  public writeValue(val: any): void {
    if (typeof val != 'undefined') {
      this.active = val;
      this.data.emit(this.active);
    }
  }

  public validate(c: FormControl): any {
    let controlValue = c ? c.value : [];
    if (!controlValue) {
      controlValue = [];
    }

    this.pCount = 0;
    this.expAttrCount = 0;
    this.active.forEach(ele => {
      if (ele.text == '(') this.pCount++;
      if (ele.text == ')') this.pCount--;
      if (ele.mode == 1 ) this.expAttrCount++;
      if (this.pCount < 0) return;
    });

    return (this.active.length ==0) || (this.getNextMode() === 4 && this.pCount === 0 && this.expAttrCount <= 5) ? null : {
      ng2SelectEmptyError: {
        valid: false
      }
    };
  }

  protected matchClick(e: any): void {
    if (this._disabled === true) {
      return;
    }
    this.inputMode = !this.inputMode;
    if (this.inputMode === true && ((this.multiple === true && e) || this.multiple === false)) {
      this.focusToInput();
      this.open();
    }
  }

  protected mainClick(event: any): void {
    if (this.inputMode === true || this._disabled === true) {
      return;
    }
    if (event.keyCode === 46) {
      event.preventDefault();
      this.inputEvent(event);
      return;
    }
    if (event.keyCode === 8) {
      event.preventDefault();
      this.inputEvent(event, true);
      return;
    }
    if (event.keyCode === 9 || event.keyCode === 13 ||
      event.keyCode === 27 || (event.keyCode >= 37 && event.keyCode <= 40)) {
      event.preventDefault();
      return;
    }
    this.inputMode = true;
    let value = String
      .fromCharCode(96 <= event.keyCode && event.keyCode <= 105 ? event.keyCode - 48 : event.keyCode)
      .toLowerCase();
    this.focusToInput(value);
    this.open();
    let target = event.target || event.srcElement;
    target.value = value;
    this.inputEvent(event);
  }

  protected selectActive(value: SelectItem): void {
    this.activeOption = value;
  }

  protected isActive(value: SelectItem): boolean {
    return this.activeOption.id === value.id;
  }

  public registerOnChange(fn: (_: any) => {}): void { this.onChange = fn; }
  public registerOnTouched(fn: () => {}): void { this.onTouched = fn; }

  protected removeClick(value: SelectItem, event: any): void {
    event.stopPropagation();
    this.remove(value);
  }

  private focusToInput(value: string = ''): void {
    setTimeout(() => {
      let el = this.element.nativeElement.querySelector('div.ui-select-container > input');
      if (el) {
        el.focus();
        this.open();
        el.value = value;
      }
    }, 0);
  }

  private open(): void {
    if (this.disabled) return;
    switch (this.mode) {
      case 1:
        this.options = this.paramObjects;
        break;
      case 2:
        this.options = this.operatorObjects;
        break;
      case 3:
        this.options = [];
        break;
      case 4:
        this.options = this.joinExpObjects;
    }

    if (this.options.length > 0) {
      this.behavior.first();
    }
    this.optionsOpened = true;
    this.outOfOption = false;
  }

  private hideOptions(): void {
    this.inputMode = false;
    this.optionsOpened = false;
  }

  private selectActiveMatch(): void {
    this.selectMatch(this.activeOption);
  }

  private selectMatch(value: SelectItem, e: Event = void 0, changeMode = true): void {
    if (e) {
      e.stopPropagation();
      e.preventDefault();
    }
    // if (this.options.length <= 0) {
    //   return;
    // }
    value.mode = this.mode;
    if (this.multiple === true) {
      if (this.isEditMode) {
        this.active[this.editPosition] = value;
        this.isEditMode = false;
        // this.editPosition = null;
      } else {
        this.active.push(value);
      }
      this.data.next(this.active);
    }
    this.doEvent('selected', value);
    this.hideOptions();

    if (this.multiple === true) {
      this.focusToInput('');
    } else {
      this.focusToInput(stripTags(value.text));
      this.element.nativeElement.querySelector('.ui-select-container').focus();
    }

    if (changeMode) {
      this.mode = this.getNextMode();
      this.open();
    }
  }

  private getNextMode() {
    let nextMode = 1;

    if (this.active && this.active.length > 0) {

      let lastNode = this.active[this.active.length - 1];
      let singleOperator = ['isnull', 'notnull'];
      if (typeof lastNode !== 'undefined') {

        let str = lastNode.id.trim().toLowerCase();
        nextMode = lastNode.mode;
      if (singleOperator.indexOf(str) != -1 && nextMode == 2) {
        nextMode++;
      }
      if (['(', ')'].indexOf(lastNode.text) == -1) {
        nextMode++;
      }
      nextMode = nextMode % 5;
      nextMode = nextMode === 0 ? 1 : nextMode;
    }
      return nextMode;
    }
  }

}

