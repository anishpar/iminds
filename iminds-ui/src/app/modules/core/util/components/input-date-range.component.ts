import { Component, OnInit, Input, forwardRef, Attribute, HostBinding, ViewChild } from '@angular/core';
import { MasterComponent } from '../../common/components/master.component';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, NgForm} from '@angular/forms';
/**
 * @author Hiren
 */
@Component({
    selector: 'stl-input-daterange',
    templateUrl: './input-date-range.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => InputDateRangeComponent),
            multi: true
        },
        {
            provide: NG_VALIDATORS,
            useExisting: forwardRef(() => InputDateRangeComponent),
            multi: true,
        }
    ],
    host: {
        '(blur)': '_onTouched()'
     }
})
export class InputDateRangeComponent extends MasterComponent implements OnInit, ControlValueAccessor   {

    @Input() disabledComponent = false;

    public dateRange: dateRangeType = {fromDate: '', toDate: ''};
    public placeHolder = this.lang.datePickerPlaceHolder;
    public readonly = false;
    public propagateChange: any;
    public errorMsg: string;
    private maxDateForEndDate: Date = null;
    private minDateForEndDate: Date = null;
    @ViewChild('dateRangeForm',  {static: false}) dateRangeForm: NgForm;

    @Input() public labelCss = 'col-md-2';
    @Input() public startCss = 'col-md-3';
    @Input() public endCss = 'col-md-3';

    public bsStartConfig = { minDate: null, maxDate: null };

    public bsEndConfig = {  minDate: null, maxDate: null };
    _onTouched: any;
    @Input() isRequired = false;
    @Input() selectLabel = this.lang.dateRange;
    @Input() maxRangeInMonth = 0;
    @Input() maxRangeInDays = 0;

    constructor() {
        super();
    }

    ngOnInit(): void {

    }
    @Input() set minDate(val) {
        this.bsStartConfig.minDate = val;
    }

    @Input() set maxDate(val) {
        this.bsEndConfig.maxDate = val;
        this.maxDateForEndDate = val;
    }

    @Input() set onlyFuture(val) {
        if (val) {
            this.bsStartConfig.minDate = new Date();
            this.bsEndConfig.minDate = new Date();
            this.minDateForEndDate = new Date();
        } else {
            this.bsStartConfig.minDate = null;
            this.bsEndConfig.minDate = null;
            this.minDateForEndDate = null;
        }
    }

    @Input() set onlyPast(val) {
        if (val) {
            this.bsStartConfig.maxDate = new Date();
            this.bsEndConfig.maxDate = new Date();
            this.maxDateForEndDate = new Date();
        } else {
            this.bsStartConfig.maxDate = null;
            this.bsEndConfig.maxDate = null;
            this.maxDateForEndDate = null;
        }
    }

    writeValue(obj: dateRangeType): void {
        if (obj && obj.fromDate && typeof obj.fromDate === 'string') {
            obj.fromDate = this.dateFromString(obj.fromDate);
        }
        if (obj && obj.toDate && typeof obj.toDate === 'string') {
            obj.toDate = this.dateFromString(obj.toDate);
        }
        this.dateRange = obj;
    }

    registerOnChange(fn: any): void {
        this.propagateChange = fn;
    }

    registerOnTouched(fn: any): void {
        this._onTouched = fn;
    }

    setDisabledState?(isDisabled: boolean): void {
        this.disabledComponent = isDisabled;
    }

    validate() {
        if (! this.dateRangeForm.valid) {
            return {
                invalidDate: {
                    valid: false,
                }
            };
        }
    }

    valueChange() {
        if (this.propagateChange) {
            this.propagateChange(this.dateRange);
        }
    }

    onFromDateChanged() {
        if (this.dateRange.fromDate) {
            this.bsEndConfig.minDate = new Date(this.dateRange.fromDate);
            // To set date range of min  months
            if (this.maxRangeInMonth > 0) {
                const tempDate: Date = new Date(this.dateRange.fromDate);
                tempDate.setMonth(tempDate.getMonth() + this.maxRangeInMonth);
                tempDate.setDate(tempDate.getDate() - 1);
                if (tempDate < (new Date())) {
                    this.bsEndConfig.maxDate = tempDate;
                } else {
                    this.bsEndConfig.maxDate = new Date;
                }
            } else if (this.maxRangeInDays > 0) {
                const tempDate2: Date = new Date(this.dateRange.fromDate);
                tempDate2.setDate(tempDate2.getDate() + this.maxRangeInDays);
                if (tempDate2 < (new Date())) {
                    this.bsEndConfig.maxDate = tempDate2;
                } else {
                    this.bsEndConfig.maxDate = new Date;
                }
            }
        } else {
            this.bsEndConfig.minDate = this.minDateForEndDate;
            this.bsEndConfig.maxDate = this.maxDateForEndDate;
        }
        this.dateRange.toDate = '';
        this.valueChange();
    }
}

interface dateRangeType {
    fromDate:any;
    toDate:any;
}