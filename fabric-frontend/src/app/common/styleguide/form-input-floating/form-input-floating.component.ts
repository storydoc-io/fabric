import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-form-input-floating',
  templateUrl: './form-input-floating.component.html',
  styleUrls: ['./form-input-floating.component.scss']
})
export class FormInputFloatingComponent {

  @Input()
  _for: string

  @Input()
  _label: string

}
