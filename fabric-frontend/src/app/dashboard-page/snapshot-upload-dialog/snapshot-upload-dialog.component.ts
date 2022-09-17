import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EnvironmentDto} from "@fabric/models";

export interface SnapshotUploadDialogData {
  environment: string,
}

export interface SnapshotUploadDialogSpec {
  environments: EnvironmentDto[]
  data: SnapshotUploadDialogData
  cancel: () => void
  confirm: (data: SnapshotUploadDialogData) => void
}

@Component({
  selector: 'app-snapshot-upload-dialog',
  templateUrl: './snapshot-upload-dialog.component.html',
  styleUrls: ['./snapshot-upload-dialog.component.scss']
})
export class SnapshotUploadDialogComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  spec: SnapshotUploadDialogSpec

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    environment : new FormControl(null, [Validators.required]),
  })

  private get environmentControl(): FormControl {
    return <FormControl> this.formGroup.get('environment')
  }

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }

}
