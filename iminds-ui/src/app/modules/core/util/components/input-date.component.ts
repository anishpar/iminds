import { Component, OnInit, Input, forwardRef } from '@angular/core';
import { MasterComponent } from '../../common/components/master.component';
import { ControlValueAccessor, Validator, NG_VALUE_ACCESSOR, NG_VALIDATORS, FormControl } from '@angular/forms';

@Component({
    selector: 'stl-input-datepicker',
    templateUrl: './input-date.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => InputDateComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => InputDateComponent),
            multi: true,
        }
    ]
})
export class InputDateComponent extends MasterComponent implements OnInit, ControlValueAccessor, Validator {

    @Input() disabledComponent = false;

    public dateValue;
    public placeHolder = this.lang.datePickerPlaceHolder;
    public readonly = false;
    public propagateChange: any;
    public errorMsg: string;
    public bsConfig = {
        dateInputFormat: 'DD/MM/YYYY'
        , containerClass: 'theme-dark-blue'
        , showWeekNumbers: false
        , minDate: null, maxDate: null
    };

    constructor() {
        super();
    }

    ngOnInit(): void {
    }

    public validate(c: FormControl): any {
        this.errorMsg = '';
        if (this.dateValue) {
            if (this.dateValue + '' === 'Invalid Date') {
                return {
                    invalidDate: {
                        valid: false,
                    },
                };
            }

            const dv: Date = this.dateValue;
            const mindateV: Date = this.bsConfig.minDate;
            const maxDateV: Date = this.bsConfig.maxDate;


            dv.setHours(0, 0, 0, 0);
            if (this.bsConfig.minDate) {
                mindateV.setHours(0, 0, 0, 0);
            }
            if (this.bsConfig.maxDate) {
                maxDateV.setHours(0, 0, 0, 0);
            }

            if ((this.bsConfig.minDate && dv < mindateV) || (this.bsConfig.maxDate && dv > maxDateV)) {
                this.errorMsg = this.lang.errInvalidDate;
                return {
                    invalidDate: {
                        valid: false,
                    },
                };
            }
        }
    }

    @Input() set minDate(val) {
        this.bsConfig.minDate = val;
    }

    @Input() set maxDate(val) {
        this.bsConfig.maxDate = val;
    }

    @Input() set onlyFuture(val) {
        if (val) {
            this.bsConfig.minDate = new Date();
        } else {
            this.bsConfig.minDate = null;
        }
    }

    @Input() set onlyPast(val) {
        if (val) {
            this.bsConfig.maxDate = new Date();
        } else {
            this.bsConfig.maxDate = null;
        }
    }

    writeValue(obj): void {
        if (obj && typeof obj === 'string') {
            obj = new Date(obj);
        }
        this.dateValue = obj;
    }

    registerOnChange(fn: any): void {
        this.propagateChange = fn;
    }

    registerOnTouched(fn: any): void {
    }

    setDisabledState?(isDisabled: boolean): void {
        this.disabledComponent = isDisabled;
    }

    dateToServerFormat(date: Date) {
        const dd = date.getDate();
        const mm = date.getMonth() + 1;
        const yy = date.getFullYear();
        const final_date = yy + '-' + (mm < 10 ? '0' + mm : mm) + '-' + (dd < 10 ? '0' + dd : dd);
        return final_date;
    }

    onValueChange(value: Date): void {
        let propogateValue = '';
        if (value) {
            // this.dateValue = value;
            propogateValue = this.dateToServerFormat(value);
        }
        if (this.propagateChange) {
            this.propagateChange(propogateValue);
        }
    }
}
