import {Component, Input, OnInit} from '@angular/core';
import {EnvironmentDialogData, EnvironmentDialogSpec} from "./environment-dialog/environment-dialog.component";
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {ModalService} from "../../../common/modal/modal-service";
import {SystemDescriptionService} from "../../system-description.service";

@Component({
  selector: 'app-environment-panel',
  templateUrl: './environment-panel.component.html',
  styleUrls: ['./environment-panel.component.scss']
})
export class EnvironmentPanelComponent extends HasConfirmationDialogMixin implements OnInit {


  constructor(modalService: ModalService, private service: SystemDescriptionService) {
    super(modalService);
  }

  ngOnInit(): void {
  }

  @Input()
  systemDescription: SystemDescriptionDto

  // environments

  environmentDialogSpec: EnvironmentDialogSpec

  environmentDialogId(): string {
    return 'environmentDialog'
  }

  private openEnvironmentDialog(spec: EnvironmentDialogSpec) {
    this.environmentDialogSpec = spec
    this.modalService.open(this.environmentDialogId())
  }

  private closeEnvironmentDialog() {
    this.environmentDialogSpec = null
    this.modalService.close(this.environmentDialogId())
  }

  addEnvironment(systemDescription: SystemDescriptionDto) {
    this.openEnvironmentDialog({
      keys: this.environmentKeys(systemDescription),
      data: {
        key: null,
        label: null,
      },
      confirm: data => {
        this.closeEnvironmentDialog()
        this.service.addEnvironment(<SystemComponentDto>data)
      },
      cancel: () => this.closeEnvironmentDialog()
    })
  }

  editEnvironment(systemDescription: SystemDescriptionDto, environment: EnvironmentDto) {
    this.openEnvironmentDialog({
      keys: this.environmentKeys(systemDescription, environment),
      data: <EnvironmentDialogData> { ... environment},
      confirm: data => {
        this.closeEnvironmentDialog()
        this.service.updateEnvironment(environment, <EnvironmentDto>data)
      },
      cancel: () => this.closeEnvironmentDialog()
    })
  }

  removeEnvironment(environment: EnvironmentDto) {
    this.openConfirmationDialog({
      title: 'Confirm delete',
      message: `Delete '${environment.label}' environment ?`,
      warning: `This will also remove datasource connection settings for this environment.`,
      confirm: () => {
        this.closeConfirmationDialog()
        this.service.deleteEnvironment(environment)
      },
      cancel: () => this.closeConfirmationDialog()
    })
  }

  private environmentKeys(systemDescription: SystemDescriptionDto, exceptEnvironment?: SystemComponentDto): string[] {
    return systemDescription.environments
        .filter(environment => environment.key != exceptEnvironment?.key)
        .map(environment => environment.key);
  }

  confirmationDialogId(): string {
    return 'confirmation-dialog-env'
  }


}
