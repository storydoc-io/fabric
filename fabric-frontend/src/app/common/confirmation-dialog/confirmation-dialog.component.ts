import {Component, Input} from '@angular/core';
import { faExclamationTriangle } from '@fortawesome/free-solid-svg-icons';

export interface ConfirmationDialogSpec {
  title: string
  message: string
  warning? : string
  cancel: () => void
  confirm: () => void
}

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent {

  @Input()
  spec: ConfirmationDialogSpec

  faExclamationTriangle=faExclamationTriangle

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm()
  }

}
