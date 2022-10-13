import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent {

  constructor() { }

  @Input()
  status: string

}
