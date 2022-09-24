import {Component, Input, OnInit} from '@angular/core';

export interface ActionSpec {
  handler: () => void
  label: string
}

export interface ActionsSpec {
  actions: ActionSpec[]
}

@Component({
  selector: 'app-action-buttons',
  templateUrl: './action-buttons.component.html',
  styleUrls: ['./action-buttons.component.scss']
})
export class ActionButtonsComponent implements OnInit {

  constructor() { }

  actions: ActionSpec[]

  @Input()
  spec: ActionsSpec

  ngOnInit(): void {
    // cfr https://stackoverflow.com/questions/71011494/angular-8-click-event-not-firing-inside-ngfor-when-iterating-over-object-array
    this.actions = this.spec.actions
  }

  callActionHandler(action: ActionSpec) {
    action.handler()
  }

}
