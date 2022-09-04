import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {logChangesToObservable} from "@fabric/common";
import {ConnectionTestResponseDto, EnvironmentDto, StructureDto, SystemComponentDto, SystemDescriptionDto, SystemTypeDescriptorDto} from "@fabric/models";
import {ConnectionControllerService, MetaModelControllerService, SystemDescriptionControllerService} from "@fabric/services";
import {SettingsDialogData} from "./settings-panel/settings-dialog/settings-dialog.component";
import {MongoMetaModelService} from "./meta-model-panel/mongo-metamodel-panel/mongo-metamodel.service";


export interface Setting {
    key: string,
    value: string
}

export interface SettingRow {
    systemComponentKey: string,
    environmentKey : string,
    settings: Setting[]
}

interface SystemDescriptionState {
    systemDescription: SystemDescriptionDto
}

@Injectable({
    providedIn: 'root'
})
export class SystemDescriptionService implements OnDestroy {

    constructor(
        private systemDescriptionControllerService: SystemDescriptionControllerService,
        private metaModelControllerService: MetaModelControllerService,
        private connectionControllerService: ConnectionControllerService,
        private mongoMetaModelService: MongoMetaModelService) {
        this.init()
    }

    private store = new BehaviorSubject<SystemDescriptionState>({systemDescription: null})

    systemDescription$ = this.store.pipe(
        map(state => state.systemDescription),
        distinctUntilChanged(),
    )

    systemTypeDescriptors$ = new BehaviorSubject<SystemTypeDescriptorDto[]>(null)

    private subscriptions: Subscription[] = []

    private init() {
        this.subscriptions.push(logChangesToObservable('systemDescriptionStore::systemDescription$ >>', this.systemDescription$))
        this.loadSystemTypeDescriptors()
        this.loadSystemDescription()
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe())
    }

    loadSystemTypeDescriptors() {
        this.systemDescriptionControllerService.getSystemTypeDescriptorsUsingGet({}).subscribe(dto => this.systemTypeDescriptors$.next(dto))
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
        return ['MONGO','ELASTICSEARCH']
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

    fetchMetaModel(systemComponent: SystemComponentDto, environmentKey: string): Promise<StructureDto> {
        return this.metaModelControllerService.createMetaModelUsingPost({
            environmentKey,
            systemComponentKey: systemComponent.key
        }).toPromise()
          .then(() => this.loadEnvironmentSystemComponentStructure(environmentKey, systemComponent.key))
    }

    testConnection(systemType: string, settings: any): Promise<ConnectionTestResponseDto> {
        return this.connectionControllerService.testConnectionUsingPost({ body: {
            settings,
            systemType
        }}).toPromise()
    }

    loadEnvironmentSystemStructure(envKey: string): Promise<StructureDto> {
        return this.systemDescriptionControllerService.getEnvironmentStructureUsingGet({ envKey }).toPromise()
    }

    loadEnvironmentSystemComponentStructure(envKey: string, systemComponentKey: string): Promise<StructureDto> {
        return this.systemDescriptionControllerService.getSystemComponentEnvironmentStructureUsingGet({ envKey, systemComponentKey }).toPromise()
    }



}
