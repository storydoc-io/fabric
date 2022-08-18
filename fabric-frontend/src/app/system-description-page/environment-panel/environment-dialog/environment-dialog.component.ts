import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {showValidationMessages, unique} from '@fabric/common'

export interface EnvironmentDialogData {
  key: string,
  label: string,
}

export interface EnvironmentDialogSpec {
  keys: string[];
  data: EnvironmentDialogData
  cancel: () => void
  confirm: (data: EnvironmentDialogData) => void
}

@Component({
  selector: 'app-environment-dialog',
  templateUrl: './environment-dialog.component.html',
  styleUrls: ['./environment-dialog.component.scss']
})
export class EnvironmentDialogComponent implements OnInit, OnChanges {

  constructor() {
  }

  ngOnInit(): void {
  }

  @Input()
  spec: EnvironmentDialogSpec

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.keyControl.setValidators([Validators.required, unique('key', this.spec.keys)])
      this.formGroup.setValue(this.spec.data)
      this.formGroup.markAsPristine()
      this.formGroup.markAsUntouched()
    }
  }

  formGroup: FormGroup = new FormGroup({
    key: new FormControl(null, [Validators.required]),
    label: new FormControl(null, [Validators.required]),
  })

  public get keyControl(): FormControl {
    return <FormControl> this.formGroup.get('key')
  }

  public keyControlInvalid() : boolean {
    return showValidationMessages(this.keyControl)
  }

  public get labelControl(): FormControl {
    return <FormControl> this.formGroup.get('label')
  }

  labelControlInvalid() {
    return showValidationMessages(this.labelControl)
  }

  private get systemTypeControl(): FormControl {
    return <FormControl> this.formGroup.get('systemType')
  }

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }

}
