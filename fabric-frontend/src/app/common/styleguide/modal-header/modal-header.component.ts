import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-modal-header',
  templateUrl: './modal-header.component.html',
  styleUrls: ['./modal-header.component.scss']
})
export class ModalHeaderComponent implements OnInit {

  @Input()
  title: string;

  @Output()
  onCancel: EventEmitter<void>

  cancel() {
    this.onCancel.emit()
  }

  constructor() { }

  ngOnInit(): void {
  }

}
