import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {DashboardService} from "../dashboard.service";
import {SystemDescriptionService} from "../../system-description-page/system-description.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface SnapshotDialogData {
    environment: string,
    name: string
}

export interface SnapshotDialogSpec {
    data: SnapshotDialogData
    cancel: () => void
    confirm: (data: SnapshotDialogData) => void

}

@Component({
    selector: 'app-snapshot-dialog',
    templateUrl: './snapshot-dialog.component.html',
    styleUrls: ['./snapshot-dialog.component.scss']
})
export class SnapshotDialogComponent implements OnInit {

    constructor(private systemDescriptService: SystemDescriptionService) {
    }

    systemDescription$ = this.systemDescriptService.systemDescription$

    ngOnInit(): void {
    }

    @Input()
    spec: SnapshotDialogSpec

    ngOnChanges(changes: SimpleChanges): void {
        if (this.spec != null) {
            this.formGroup.setValue(this.spec.data)
        }
    }

    formGroup: FormGroup = new FormGroup({
        environment : new FormControl(null, [Validators.required]),
        name : new FormControl(null, [Validators.required]),
    })

    cancel() {
        this.spec.cancel()
    }

    confirm() {
        this.spec.confirm(this.formGroup.value)
    }
}
