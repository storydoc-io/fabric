import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {showValidationMessages} from "@fabric/common";
import {SystemDescriptionWrapper} from "../../system-description.service";


export interface MetaModelDialogData {
  environmentKey: string
}

export interface MetaModelDialogSpec {
  systemComponent: SystemComponentDto
  systemDescription: SystemDescriptionDto
  data: MetaModelDialogData
  cancel: () => void
  confirm: (data: MetaModelDialogData) => void
}

@Component({
  selector: 'app-meta-model-dialog',
  templateUrl: './meta-model-dialog.component.html',
  styleUrls: ['./meta-model-dialog.component.scss']
})
export class MetaModelDialogComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  spec: MetaModelDialogSpec

  formGroup: FormGroup = new FormGroup({
    environmentKey: new FormControl(null, [Validators.required]),
  })

  public get environmentKeyControl(): FormControl {
    return <FormControl> this.formGroup.get('environmentKey')
  }

  environmentKeyControlInvalid() {
    return showValidationMessages(this.environmentKeyControl)
  }

  availableEnvironments(): EnvironmentDto[] {
    return new SystemDescriptionWrapper(this.spec.systemDescription).getEnvironments(this.spec.systemComponent);
  }

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }


}
