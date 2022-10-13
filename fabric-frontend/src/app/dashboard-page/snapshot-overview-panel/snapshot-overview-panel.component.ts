import {Component, OnInit} from '@angular/core';
import {SnapshotId, SnapshotSummaryDto, SystemDescriptionDto} from "@fabric/models";
import {ModalService} from "../../common/modal/modal-service";
import {DashboardService} from "../dashboard.service";
import {SnapshotDialogData, SnapshotDialogSpec} from "../snapshot-dialog/snapshot-dialog.component";
import {ActionsSpec, HasConfirmationDialogMixin} from "@fabric/common";
import {SnapshotUploadDialogSpec} from "../snapshot-upload-dialog/snapshot-upload-dialog.component";
import {SystemDescriptionService} from "../../system-description-page/system-description.service";
import {CommandControllerService} from "@fabric/services";
import {Router} from "@angular/router";

@Component({
  selector: 'app-snapshot-overview-panel',
  templateUrl: './snapshot-overview-panel.component.html',
  styleUrls: ['./snapshot-overview-panel.component.scss']
})
export class SnapshotOverviewPanelComponent extends HasConfirmationDialogMixin implements OnInit {

  constructor(
      public modalService: ModalService,
      private service: DashboardService,
      private systemDescriptionService : SystemDescriptionService,
      private commandControllerService: CommandControllerService,
      private _router: Router
  ) {
    super(modalService)
  }

  ngOnInit(): void {
  }

  // summaries
  summaries$ = this.service.summaries$
  systemDescription$ = this.systemDescriptionService.systemDescription$

  summaryActions(summary: SnapshotSummaryDto, systemDesc: SystemDescriptionDto): ActionsSpec {
      return {
        actions: [
          {
            label: 'Delete',
            handler: () => this.openConfirmationDialog({
              title : 'Confirm Delete',
              message: `Delete Snapshot ${summary.name}?`,
              confirm: () => { this.closeConfirmationDialog(); this.confirmDelete(summary.snapshotId) },
              cancel: () => this.closeConfirmationDialog()
            })
          },
          {
            label: 'Upload',
            handler: () => this.openUploadDialog({
              environments: systemDesc.environments,
              data: {
                environment: null
              },
              cancel: () => this.closeUploadDialog(),
              confirm: (data) => {
                this.closeUploadDialog()
                this.confirmUpload(summary.snapshotId, data.environment)
              }
            })
          }
        ]
      }
  }

  private confirmDelete(snapshotId: SnapshotId) {
    this.service.delete(snapshotId)
  }

  private confirmUpload(snapshotId: SnapshotId, envkKey: string) {
    this.createDummySnapshot(snapshotId, envkKey)
  }

  createSnapshot() {
    this.openSnapshotDialog({
      useNameGenerator: true,
      data: {
        environment: null,
        name: null
      },
      cancel: () => this.closeSnapshotDialog(),
      confirm: (data) => { this.confirmCreate(data) ; this.closeSnapshotDialog() }
    })
  }

  private confirmCreate(data: SnapshotDialogData) {
    // this.service.createSnapshot(data.environment, data.name)
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

  // upload dialog

  uploadDialogSpec: SnapshotUploadDialogSpec

  snapshotUploadDialogId() {
    return 'snapshot-upload-dialog'
  }

  private openUploadDialog(spec: SnapshotUploadDialogSpec) {
    this.uploadDialogSpec = spec
    this.modalService.open(this.snapshotUploadDialogId())
  }

  private closeUploadDialog() {
    this.modalService.close(this.snapshotUploadDialogId())
  }

  // deletion confirmation dialog

  confirmationDialogId(): string {
    return 'delete-snapshot-confirmation-dialog'
  }

  createDummySnapshot(snapshotId: SnapshotId, envKey: string) {
        this.service.upload(snapshotId, envKey).then((executionId)=> {
            this._router.navigate(["fe", "dummy", executionId.id] )
        })
    }
}
