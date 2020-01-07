import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { MasterComponent } from '../../../core/common/components/master.component';
import { SharedService } from '../services/shared.service';
import { takeUntil } from 'rxjs/operators';
import { PaginationConfig } from '../../../core/util/configuration/pagination.config';

@Component({
    selector: 'stl-all-history-component',
    templateUrl: './all-history.component.html'

})
export class AllHistoryComponent extends MasterComponent implements OnInit, OnDestroy {


    @Input() orderNumber;

    @Input() moduleName;

    @Input() entityHistoryTitle;

    @Input() referenceNumber;

    public entityHistoryData: any;
    public woHistoryData: any;
    public woPagination;
    public enPagination;


    constructor(private service: SharedService) {
        super();
        this.woPagination = Object.assign({}, PaginationConfig);
        this.enPagination = Object.assign({}, PaginationConfig);
    }

    public flag = true;


    ngOnInit() {
        this.woPagination = Object.assign({}, PaginationConfig);
        if (this.orderNumber) {
            this.service.getOrderHistory({ 'orderNumber': this.orderNumber }).
                pipe(takeUntil(this.onDestroy$)).subscribe(getOrderHistory => {
                    this.woHistoryData = getOrderHistory;
                });
        }
    }


    trackbyfn(index) {
        return index;
    }

    ngOnDestroy() {
        this.manageDestroy();
    }
}
