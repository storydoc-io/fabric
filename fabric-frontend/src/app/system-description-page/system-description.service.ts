import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {logChangesToObservable} from "@fabric/common";
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {SystemDescriptionControllerService} from "@fabric/services";
import {SettingsDialogData} from "./settings-panel/settings-dialog/settings-dialog.component";


export interface Setting {
    key: string,
    value: string
}

export interface SettingRow {
    systemComponentKey: string,
    environmentKey : string,
    settings: Setting[]
}

export interface SettingDescriptor {
    key: string,
    description: string
}

interface SystemDescriptionState {
    systemDescription: SystemDescriptionDto
}

@Injectable({
    providedIn: 'root'
})
export class SystemDescriptionService implements OnDestroy {

    constructor(private systemDescriptionControllerService: SystemDescriptionControllerService) {
        this.init()
    }

    private store = new BehaviorSubject<SystemDescriptionState>({systemDescription: null})

    systemDescription$ = this.store.pipe(
        map(state => state.systemDescription),
        distinctUntilChanged(),
    )

    private subscriptions: Subscription[] = []

    private init() {
        this.subscriptions.push(logChangesToObservable('systemDescriptionStore::systemDescription$ >>', this.systemDescription$))
        this.loadSystemDescription()
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe())
    }

    loadSystemDescription() {
        this.systemDescriptionControllerService.getSystemDescriptionUsingGet({}).subscribe(dto => {
            this.store.next({systemDescription: dto})
        })
    }

    private get systemDescription() : SystemDescriptionDto {
        return this.store.value.systemDescription
    }

    private saveSystemDescription(systemDescription: SystemDescriptionDto) {
        this.systemDescriptionControllerService.setSystemDescriptionUsingPost({
            body : this.systemDescription
        }).subscribe(() => this.loadSystemDescription())
    }

    addSystemComponent(systemComponent: SystemComponentDto) {
        this.systemDescription.systemComponents.push(systemComponent)
        this.saveSystemDescription(this.systemDescription)
    }

    deleteSystemComponent(systemComponent: SystemComponentDto) {
        this.systemDescription.systemComponents = this.systemDescription.systemComponents.filter((val => val.key != systemComponent.key))
        this.systemDescription.environments.forEach(environment => {
            let envSettings = this.systemDescription.settings[environment.key]
            if (envSettings) {
                delete envSettings[systemComponent.key]
            }
        })

        this.saveSystemDescription(this.systemDescription)
    }

    updateSystemComponent(old: SystemComponentDto, updated: SystemComponentDto) {
        let systemDescription: SystemDescriptionDto = this.systemDescription
        let idx = systemDescription.systemComponents.findIndex((val => val.key === old.key))
        systemDescription.systemComponents[idx] = updated
        this.saveSystemDescription(systemDescription)
    }

    addEnvironment(environment: EnvironmentDto) {
        this.systemDescription.environments.push(environment)
        this.saveSystemDescription(this.systemDescription)
    }

    deleteEnvironment(environment: EnvironmentDto) {
        let systemDescription: SystemDescriptionDto = this.systemDescription
        systemDescription.environments = systemDescription.environments.filter((val => val.key != environment.key))
        delete systemDescription.settings[environment.key]
        this.saveSystemDescription(systemDescription)
    }

    updateEnvironment(old: EnvironmentDto, updated: EnvironmentDto) {
        let systemDescription: SystemDescriptionDto = this.systemDescription
        let idx = systemDescription.environments.findIndex((val => val.key === old.key))
        systemDescription.environments[idx] = updated
        this.saveSystemDescription(systemDescription)
    }


    addSetting(data: SettingsDialogData) {
        let settingObject = {}
        data.settings.forEach(setting => {
            settingObject[setting.key] = setting.value
        })

        let systemDescription: SystemDescriptionDto = this.systemDescription
        if (!systemDescription.settings[data.environmentKey]) {
            systemDescription.settings[data.environmentKey] = {}
        }
        systemDescription.settings[data.environmentKey][data.systemComponentKey] = settingObject
        this.saveSystemDescription(systemDescription)
    }

    updateSetting(data: SettingsDialogData) {
        let settingObject = {}
        data.settings.forEach(setting => {
            settingObject[setting.key] = setting.value
        })

        let systemDescription: SystemDescriptionDto = this.systemDescription
        if (!systemDescription.settings[data.environmentKey]) {
            systemDescription.settings[data.environmentKey] = {}
        }
        systemDescription.settings[data.environmentKey][data.systemComponentKey] = settingObject
        this.saveSystemDescription(systemDescription)
    }

    deleteSetting(data: SettingsDialogData) {
        delete (this.systemDescription.settings[data.environmentKey])[data.systemComponentKey]
        this.saveSystemDescription(this.systemDescription)
    }

    getSystemTypes(): string[] {
        return ['MONGO']
    }

    getSettingDescriptors(systemType: string) {
        if (systemType === 'MONGO') {
            return [
                {
                    key: 'connectionUrl',
                    description: 'enter a connection url to the mongo db'
                },
                {
                    key: 'dbName',
                    description: 'enter the mongo database'
                }
            ]
        } else
            return [];
    }
}
