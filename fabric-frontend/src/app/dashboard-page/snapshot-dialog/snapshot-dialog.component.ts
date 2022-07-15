import {Component, Input, OnInit} from '@angular/core';

export interface SnapshotDialogData {

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

    constructor() {
    }

    ngOnInit(): void {
    }

    @Input()
    spec: SnapshotDialogSpec

    cancel() {
        this.spec.cancel()
    }

    confirm() {
        this.spec.confirm(this.spec.data)
    }
}
