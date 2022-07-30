import { Component, OnInit } from '@angular/core';
import { DashboardService } from "./dashboard.service";
import {ModalService} from "../common/modal/modal-service";
import {SnapshotDialogData, SnapshotDialogSpec} from "./snapshot-dialog/snapshot-dialog.component";
import {SystemDescriptionService} from "../system-description-page/system-description.service";

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss']
})
export class DashboardPageComponent implements OnInit {

  constructor(private modalService: ModalService, private service: DashboardService) { }

  ngOnInit(): void {
  }

  // summaries
  summaries$ = this.service.summaries$

  createSnapshot() {
    this.openSnapshotDialog({
      data: {
        environment: null,
        name: null
      },
      cancel: () => this.closeSnapshotDialog(),
      confirm: (data) => { this.confirmCreate(data) ; this.closeSnapshotDialog() }
    })
  }

  private confirmCreate(data: SnapshotDialogData) {
    this.service.createSnapshot(data.environment, data.name)
  }

  // snapshot dialog

  spec: SnapshotDialogSpec

  snapshotDialogId() {
    return 'snapshot-dialog'
  }

  private openSnapshotDialog(spec: SnapshotDialogSpec) {
    this.spec = spec
    this.modalService.open(this.snapshotDialogId())
  }

  private closeSnapshotDialog() {
    this.modalService.close(this.snapshotDialogId())
  }


}
