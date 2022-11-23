import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ConsoleDescriptorDto} from "@fabric/models";
import {showValidationMessages} from "@fabric/common";

export interface SnippetDialogData {
  id?: string
  title?: string
  attributes?: { [key: string]: string };
  fields?: string[]
}

export interface SnippetDialogSpec {
  mode: 'NEW' | 'EDIT'
  descriptor: ConsoleDescriptorDto
  data: SnippetDialogData
  cancel: () => void
  confirm: (data: SnippetDialogData) => void
}

@Component({
  selector: 'app-snippet-dialog',
  templateUrl: './snippet-dialog.component.html',
  styleUrls: ['./snippet-dialog.component.scss']
})
export class SnippetDialogComponent implements OnChanges {

  constructor() { }

  @Input()
  spec: SnippetDialogSpec

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec) {
      this.idControl.setValue(null)
      this.titleControl.setValue(null)
      this.fieldsControl.controls = []
      if (this.spec.mode === 'EDIT') {
        this.idControl.setValue(this.spec.data.id)
        this.titleControl.setValue(this.spec.data.title)
      }
      this.spec.descriptor.items.forEach(item => {
          let value = this.spec.data.attributes[item.name]
          this.fieldsControl.push(new FormControl(value))
      })
    }
  }

  formGroup: FormGroup = new FormGroup({
    id: new FormControl(null),
    title: new FormControl(null, [Validators.required]),
    fields: new FormArray([])
  })

  get idControl():FormControl {
    return <FormControl> this.formGroup.get('id')
  }

  get titleControl():FormControl {
    return <FormControl> this.formGroup.get('title')
  }

  titleControlInvalid() {
    return showValidationMessages(this.titleControl)
  }


  get fieldsControl():FormArray {
    return <FormArray> this.formGroup.get('fields')
  }

  fieldControl(i: number): FormControl {
    return <FormControl> this.fieldsControl.controls[i]
  }

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }

}
