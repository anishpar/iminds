import { Component, OnInit, Input, forwardRef } from '@angular/core';
import { MasterComponent } from '../../common/components/master.component';

import { ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, Validator, FormControl } from '@angular/forms';


@Component({
    selector: 'stl-input-typeahead',
    templateUrl: './input-typeahead.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => InputTypeaheadComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => InputTypeaheadComponent),
            multi: true,
        }
    ]
})
export class InputTypeaheadComponent extends MasterComponent implements OnInit, ControlValueAccessor, Validator   {

    @Input() disabledComponent = false;   // if true will disable component
    @Input() required;
    @Input() label = this.lang.selectOption;  // placeholder
    @Input() labelField = 'name';       // label field name
    @Input() valueField = 'id';         // value field name for comparison
    @Input() sortOptions = true;        // if true optionlist will be sorted alphabatically
    @Input() returnField;               // if return field is specified object property would be return o/w whole object would be returned
    public _options;
    public originalOptions;

    public inputValue;
    public readonly = false;
    public propagateChange: any;
    public errorMsg: string;

    constructor() {
        super();
    }

    ngOnInit(): void {
    }

    // List of option object
    @Input() set optionList(val) {
        
        if (Array.isArray(val) && val.length > 0) {
            if (this.sortOptions) {
                val = val.sort((a, b) => {
                    const textA = a[this.labelField] ? a[this.labelField].toUpperCase() : '';
                    const textB =  b[this.labelField] ? b[this.labelField].toUpperCase() : '';
                    return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
                });
            }
            this.originalOptions = val;
            let defaultFound = val.filter(ele => ele[this.labelField] == this.label).length > 0;
            if (defaultFound)
                val = val.filter(ele => ele[this.labelField] != this.label);
            
            let defaultObje = {};
            defaultObje[this.valueField] = 'OPTION_ALL';
            defaultObje[this.labelField] = this.label;
            val.unshift(defaultObje);
            
            this._options = val.map(ele => {
                return {id : ele[this.valueField], text: ele[this.labelField] };
            });
        } else {
            this._options = [];
            this.inputValue = [];
        }
    }
    get optionList() {
        return this._options;
    }

    writeValue(obj): void {
        if (obj) {
            let selectedIds = [];
            if ( Array.isArray(obj)) {
                if (this.returnField) {
                    selectedIds = obj;
                } else {
                    selectedIds = obj.map(ele => ele.id);
                }
            } else {
                if (this.returnField) {
                    selectedIds = [obj];
                } else if (typeof obj[this.valueField] !== 'undefined') {
                    selectedIds = [obj[this.valueField]];
                }
            }
            if (this._options && this._options.length > 0) {
                this.inputValue = this._options.filter(ele => {
                    return selectedIds.indexOf(ele.id) !== -1;
                });
            } else {
                this.inputValue = [];
            }
        } else if (this.returnField) {
            this.inputValue = '';
        }
    }
    registerOnChange(fn: any): void {
        this.propagateChange = fn;
    }
    registerOnTouched(fn: any): void {
    }
    setDisabledState?(isDisabled: boolean): void {
        this.disabledComponent = isDisabled;
    }

    public validate(c: FormControl): any {
        return null;
    }

    selectedOption(data) {
        const temp = data;

        data = (Array.isArray(data) && data.length > 0) ? data[0] : data;
        if (data && typeof data.id !== 'undefined') {

            let sendData = [];
            if (this.originalOptions && this.originalOptions.length > 0) {
                sendData = this.originalOptions.filter(ele => {
                    return ele[this.valueField] === data.id;
                });
            }
            if (sendData.length > 0) {
                sendData = sendData[0];
            }
            let returnData;
            if (sendData && this.returnField && typeof sendData[this.returnField] !== 'undefined') {
                returnData = sendData[this.returnField];
            } else {
                returnData = sendData;
            }
            if (returnData === 'OPTION_ALL') { 
                if (this.propagateChange) {
                    this.propagateChange('');
                }
                return;
            }
            if (this.propagateChange) {
                this.propagateChange(returnData);
            }
        }
    }

    removed(data) {
        let emptyData;

        if (this.propagateChange && this.returnField) {
                emptyData = '';
                this.inputValue = '';
                this.propagateChange(emptyData);
            } else if (this.propagateChange) {
                emptyData = null;
                this.inputValue = null;
            this.propagateChange(emptyData);
        }
    }

}
