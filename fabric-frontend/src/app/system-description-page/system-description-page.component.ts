import {Component, OnInit} from '@angular/core';
import {SystemDescriptionService} from "./system-description.service";
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {SystemComponentDialogData, SystemComponentDialogSpec} from "./system-component-dialog/system-component-dialog.component";
import {ModalService} from "../common/modal/modal-service";
import {ConfirmationDialogSpec} from "../common/confirmation-dialog/confirmation-dialog.component";
import {EnvironmentDialogData, EnvironmentDialogSpec} from "./environment-dialog/environment-dialog.component";
import {Setting, SettingsDialogSpec} from "./settings-dialog/settings-dialog.component";

interface SettingRow {
    systemComponentKey: string,
    environmentKey : string,
    settings: Setting[]
}


@Component({
    selector: 'app-system-description-page',
    templateUrl: './system-description-page.component.html',
    styleUrls: ['./system-description-page.component.scss']
})
export class SystemDescriptionPageComponent implements OnInit {

    constructor(private modalService: ModalService, private service: SystemDescriptionService) {
    }

    systemDescription$ = this.service.systemDescription$

    ngOnInit(): void {
    }

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
            message: `Delete environment ${environment.key}?`,
            warning: `This will also remove ${environment.key} connection settings`,
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

    // confirmation dialog

    confirmationDialogSpec: any;

    confirmationDialogId(): string {
        return 'confirmation-dialog'
    }

    openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
        this.confirmationDialogSpec = confirmationDialogSpec
        this.modalService.open(this.confirmationDialogId())
    }

    closeConfirmationDialog() {
        this.modalService.close(this.confirmationDialogId())
    }

}
