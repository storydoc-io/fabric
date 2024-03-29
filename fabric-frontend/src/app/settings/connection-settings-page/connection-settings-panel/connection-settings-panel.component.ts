import {Component, Input, OnInit} from '@angular/core';
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto, SystemTypeDescriptorDto} from "@fabric/models";
import {HasConfirmationDialogMixin, ModalService} from "@fabric/common";
import {SettingRow, SystemDescriptionService, SystemDescriptionWrapper} from "../../system-description.service";
import {SettingsDialogSpec} from "./connection-settings-dialog/connection-settings-dialog.component";

@Component({
  selector: 'app-settings-panel',
  templateUrl: './connection-settings-panel.component.html',
  styleUrls: ['./connection-settings-panel.component.scss']
})
export class ConnectionSettingsPanelComponent extends HasConfirmationDialogMixin implements OnInit {

  constructor(modalService: ModalService, private service: SystemDescriptionService) {
    super(modalService);
  }

  @Input()
  systemTypes: SystemTypeDescriptorDto[]

  @Input()
  systemDescription: SystemDescriptionDto

  @Input()
  systemComponent: SystemComponentDto

  ngOnInit(): void {
  }

  // connection settings

  settingsDialogSpec: SettingsDialogSpec

  settingsDialogId(): string {
    return 'settings-dialog'
  }

  private openSettingsDialog(spec: SettingsDialogSpec) {
    this.settingsDialogSpec = spec
    this.modalService.open(this.settingsDialogId())
  }

  private closeSettingsDialog() {
    this.settingsDialogSpec = null
    this.modalService.close(this.settingsDialogId())
  }

  public addSetting(systemDescription: SystemDescriptionDto) {
    this.openSettingsDialog({
      mode: 'ADD',
      systemTypes: this.systemTypes,
      environments: this.availableEnvironments(),
      systemComponents: systemDescription.systemComponents,
      data: {
        environmentKey: null,
        systemComponentKey: this.systemComponent.key,
        settings: []
      },
      confirm: (data) => {
        this.service.addSetting(data)
        this.closeSettingsDialog()
      },
      cancel: () => this.closeSettingsDialog()
    })
  }

  public editSetting(systemDescription: SystemDescriptionDto, setting: SettingRow) {
    this.openSettingsDialog({
      mode: 'EDIT',
      systemTypes: this.systemTypes,
      environments: systemDescription.environments,
      systemComponents: systemDescription.systemComponents,
      data: {
        environmentKey: setting.environmentKey,
        systemComponentKey: setting.systemComponentKey,
        settings: setting.settings
      },
      confirm: data => {
        this.service.updateSetting(data)
        this.closeSettingsDialog()
      },
      cancel: () => this.closeSettingsDialog()
    })
  }

  public removeSetting(systemDescription: SystemDescriptionDto, setting: SettingRow) {
    this.openConfirmationDialog({
      title: 'Confirm delete',
      message: `Delete ${setting.systemComponentKey} ${setting.environmentKey}?`,
      confirm: () => {
        this.service.deleteSetting(setting)
        this.closeConfirmationDialog()
      },
      cancel: () => this.closeConfirmationDialog()
    })

  }

  availableEnvironments(): EnvironmentDto[] {
    let takenEnvKeys: string[] = this.settingRowsForSystemComponent().map(row => row.environmentKey)
    return this.systemDescription.environments.filter(env =>
        !takenEnvKeys.find((takenEnv) => takenEnv === env.key)
    )
  }

  public settingRowsForSystemComponent(): SettingRow[] {
    return new SystemDescriptionWrapper(this.systemDescription).settingRowsForSystemComponent(this.systemComponent)
  }


  confirmationDialogId(): string {
    return 'confirmation-dialog-settings'
  }

}
