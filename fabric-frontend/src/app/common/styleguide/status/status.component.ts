import {Component, Input} from '@angular/core';
import {faArrowUp, faExclamationTriangle} from '@fortawesome/free-solid-svg-icons';

export interface ConnectionStatus {
  status: 'Calling' | 'OK' | 'Problem'
  msg: string
}

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent {

  faExclamationTriangle = faExclamationTriangle
  faArrowUp = faArrowUp

  constructor() { }

  @Input()
  status: ConnectionStatus


}
