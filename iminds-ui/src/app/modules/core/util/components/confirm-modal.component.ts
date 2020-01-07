import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { MasterComponent } from '../../common/components/master.component';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'stl-confirm-modal-component',
  templateUrl: './confirm-modal.component.html'
})
export class ConfirmModalComponent extends MasterComponent implements OnInit {

  @Output() closeModal = new EventEmitter();
  @ViewChild('modal',  {static: false}) public modal: ModalDirective;
  @Input() confirmMessage: string;
  public modalConfig = { 'backdrop': 'static', 'keyboard': true };
  public showChildModal(): void {
    setTimeout(() => this.modal.show(), 1000);
  }

  public hideChildModal(e): void {
    this.closeModal.emit(e);
    this.modal.hide();
  }
  constructor() {
    super();
  }

  ngOnInit() {
    this.showChildModal();
  }
}
