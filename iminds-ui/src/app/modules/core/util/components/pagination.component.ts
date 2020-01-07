import { Component, OnInit, Input, forwardRef, Output, EventEmitter } from '@angular/core';
import { MasterComponent } from '../../common/components/master.component';

import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';


@Component({
    selector: 'stl-pagination',
    templateUrl: './pagination.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => PaginationComponent),
            multi: true
        }
    ]
})
export class PaginationComponent extends MasterComponent implements OnInit, ControlValueAccessor  {

    @Input() totalItems;
    @Input() itemsPerPage;
    @Input() currentPage;
    @Input() maxSize;
    @Input() boundaryLinks = false;
    @Input() disabledComponent = false;

    @Output() numPages = new EventEmitter();
    @Output() pageChanged = new EventEmitter();

    public propagateChange: any;

    constructor() {
        super();
    }

    ngOnInit(): void {
    }


    writeValue(obj): void {
        this.currentPage = obj;
    }
    registerOnChange(fn: any): void {
        this.propagateChange = fn;
    }
    registerOnTouched(fn: any): void {
    }
    setDisabledState?(isDisabled: boolean): void {
        this.disabledComponent = isDisabled;
    }
    curerntPageChanged(data) {
        this.pageChanged.emit(data);
        this.propagateChange(data.page);
    }
    totalPageChanged(total) {
        this.numPages.emit(total);
    }

}
