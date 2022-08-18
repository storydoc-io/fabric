import {Component, Input, OnInit} from '@angular/core';
import {SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {SystemComponentDialogData, SystemComponentDialogSpec} from "./system-component-dialog/system-component-dialog.component";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {ModalService} from "../../common/modal/modal-service";
import {SystemDescriptionService} from "../system-description.service";

@Component({
  selector: 'app-system-component-panel',
  templateUrl: './system-component-panel.component.html',
  styleUrls: ['./system-component-panel.component.scss']
})
export class SystemComponentPanelComponent extends HasConfirmationDialogMixin implements OnInit {

  constructor(modalService: ModalService, private service: SystemDescriptionService) {
    super(modalService);
  }

  ngOnInit(): void {
  }

  @Input()
  systemDescription: SystemDescriptionDto

  // data sources

  systemComponentDialogSpec: SystemComponentDialogSpec

  systemComponentDialogId(): string {
    return 'system-component-dialog'
  }

  private openSystemComponentDialog(spec: SystemComponentDialogSpec) {
    this.systemComponentDialogSpec = spec
    this.modalService.open(this.systemComponentDialogId())
  }

  private closeSystemComponentDialog() {
    this.systemComponentDialogSpec = null
    this.modalService.close(this.systemComponentDialogId())
  }

  addComponent(systemDescription: SystemDescriptionDto) {
    this.openSystemComponentDialog({
      keys: this.systemComponentKeys(systemDescription),
      data: {
        key: null,
        label: null,
        systemType: null
      },
      confirm: data => {
        this.closeSystemComponentDialog()
        this.service.addSystemComponent(<SystemComponentDto>data)
      },
      cancel: () => this.closeSystemComponentDialog()
    })
  }

  editComponent(systemDescription: SystemDescriptionDto, systemComponent: SystemComponentDto) {
    this.openSystemComponentDialog({
      keys: this.systemComponentKeys(systemDescription, systemComponent),
      data: <SystemComponentDialogData> { ... systemComponent},
      confirm: data => {
        this.closeSystemComponentDialog()
        this.service.updateSystemComponent(systemComponent, <SystemComponentDto>data)
      },
      cancel: () => this.closeSystemComponentDialog()
    })
  }


  removeComponent(systemComponent: SystemComponentDto) {
    this.openConfirmationDialog({
      title: 'Confirm delete',
      message: `Delete datasource ${systemComponent.key}?`,
      warning: `This will also remove ${systemComponent.key}'s connection settings`,
      confirm: () => {
        this.closeConfirmationDialog()
        this.service.deleteSystemComponent(systemComponent)
      },
      cancel: () => this.closeConfirmationDialog()
    })
  }

  private systemComponentKeys(systemDescription: SystemDescriptionDto, exceptSystemComponent?: SystemComponentDto): string[] {
    return systemDescription.systemComponents
        .filter(systemComponent => systemComponent.key != exceptSystemComponent?.key)
        .map(systemComponent => systemComponent.key);
  }

  confirmationDialogId(): string {
    return 'confirmation-dialog-systemcomponent'
  }


}
