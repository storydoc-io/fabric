import {Component, Input, OnInit} from '@angular/core';
import {SystemDescriptionDto} from "@fabric/models";
import {ModalService} from "../../common/modal/modal-service";
import {Setting, SettingRow, SystemDescriptionService} from "../system-description.service";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {SettingsDialogSpec} from "./settings-dialog/settings-dialog.component";

@Component({
  selector: 'app-settings-panel',
  templateUrl: './settings-panel.component.html',
  styleUrls: ['./settings-panel.component.scss']
})
export class SettingsPanelComponent extends HasConfirmationDialogMixin implements OnInit {

  constructor(modalService: ModalService, private service: SystemDescriptionService) {
    super(modalService);
  }

  @Input()
  systemDescription: SystemDescriptionDto

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
      environments: systemDescription.environments,
      systemComponents: systemDescription.systemComponents,
      data: {
        environmentKey: null,
        systemComponentKey: null,
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

  public settingRows(systemDescription: SystemDescriptionDto): SettingRow[] {
    let settingRows: SettingRow[] = []
    Object.keys(systemDescription.settings).map(environmentKey => {
      let envSettings = systemDescription.settings[environmentKey]
      Object.keys(envSettings).map(systemComponentKey => {
        let settingsArray: Setting[] = []
        let settingsDto = envSettings[systemComponentKey]
        Object.keys(settingsDto).map(key => {
          settingsArray.push({
            key,
            value: settingsDto[key]
          })
        })
        settingRows.push({
          environmentKey,
          systemComponentKey,
          settings: settingsArray
        })
      })
    })
    return settingRows
  }

  confirmationDialogId(): string {
    return 'confirmation-dialog-settings'
  }

}
