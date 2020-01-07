import { Directive, ElementRef, forwardRef, Input } from '@angular/core';
import { Validator, AbstractControl, NG_VALIDATORS } from '@angular/forms';
import { stlConfigurations } from '../../../../config/config';
import { MasterComponent } from '../../common/components/master.component';
@Directive({
    selector: '[stlValidate]',
    providers: [
        { provide: NG_VALIDATORS, useExisting: forwardRef(() => StlValidatorDirective), multi: true }
    ]
})
export class StlValidatorDirective implements Validator {
    @Input() stlValidate: string;
    @Input() stlLabel;
    private isRequired: boolean;
    private patternName: string;
    private master;
    constructor(
         public el: ElementRef
    ) {
        this.master = new MasterComponent();
    }
    validate(control: AbstractControl): { [key: string]: any } {
        const val = control.value;
        const temp_arr = this.stlValidate.split('||');
        this.isRequired = temp_arr[0] === 'REQ';
        this.patternName = temp_arr.length > 0 ? temp_arr[1] : '';
        if (this.isRequired) {
            this.el.nativeElement.addEventListener ('blur', () => this.getErrorMsgElement().classList.remove('hide'), false);

            if (val == '' || val == null || val.toString().trim() == '') {
                this.showErrorMsg('REQ');
                return {
                    stlValidateRequired: false
                };
            }
        }
        if (this.patternName !== '' && ( (val != '' && val != null ))) {
            this.el.nativeElement.addEventListener ('blur', () => this.getErrorMsgElement().classList.remove('hide'), false);
            this.el.nativeElement.addEventListener ('keydown', () => this.getErrorMsgElement().classList.remove('hide'), false);
            this.el.nativeElement.addEventListener ('change', () => this.getErrorMsgElement().classList.remove('hide'), false);

            const pattern: RegExp = stlConfigurations.regex[this.patternName];
            if (pattern && !pattern.test(val) && val != '' ) {
                this.showErrorMsg(this.patternName);
                return {
                    stlValidatePattern: false
                };
            }
        }

        this.removeErrorMsgElement();
        return null;
    }

    private showErrorMsg(msgKey: string) {
        this.getErrorMsgElement().innerHTML = this.getErrorMsg(msgKey);
    }
    private getErrorMsgElement() {
        const errorElementList = this.el.nativeElement.parentNode.getElementsByClassName('error-span');
        return errorElementList.length ? errorElementList[0] : this.createErrorElement();
    }

    private createErrorElement() {
        const errorSpan = document.createElement('span');
        errorSpan.setAttribute('class', 'text-danger error-span hide');
        return this.el.nativeElement.parentNode.appendChild(errorSpan);
    }
    private removeErrorMsgElement() {
        // this.getErrorMsgElement().innerHTML = '&nbsp;';
        this.getErrorMsgElement().remove();
    }
    private getErrorMsg(msgKey: string) {
        let errMsg: string  = this.master.lang.errors[msgKey] ? this.master.lang.errors[msgKey] : this.master.lang.errors.common;

        errMsg = errMsg.replace('LABEL', (this.stlLabel ? this.stlLabel : 'Field'));
        return errMsg;
    }
}
