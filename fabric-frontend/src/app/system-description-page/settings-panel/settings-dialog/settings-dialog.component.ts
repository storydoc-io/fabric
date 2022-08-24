import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {AbstractControl, FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {showValidationMessages} from "@fabric/common";
import {ConnectionTestResponseDto, EnvironmentDto, SystemComponentDto} from "@fabric/models";
import {Setting, SettingDescriptor, SystemDescriptionService} from "../../system-description.service";
import {faBolt, faCheckCircle} from '@fortawesome/free-solid-svg-icons';


export interface SettingsDialogData {
    systemComponentKey: string,
    environmentKey: string,
    settings: Setting[]
}

class ConnectionTester {
    constructor(private service: SystemDescriptionService) {}

    testRunning: boolean = false
    testResult: ConnectionTestResponseDto = null

    run(settingObject: {}) {
        this.testRunning = true
        this.testResult = null
        this.service.test('MONGO', settingObject).then((result)=>  {
            this.testRunning = false
            this.testResult = result
        })
    }
}

export interface SettingsDialogSpec {
    systemComponents: SystemComponentDto[];
    environments: EnvironmentDto[];
    data: SettingsDialogData
    cancel: () => void
    confirm: (data: SettingsDialogData) => void
}

class SettingsDialogSpecWrapper {
    constructor(private spec: SettingsDialogSpec) {
    }

    getSystemType(systemComponentKey: string): string {
        return this.spec.systemComponents.find(systemComponent => systemComponent.key === systemComponentKey)?.systemType
    }
}


@Component({
    selector: 'app-settings-dialog',
    templateUrl: './settings-dialog.component.html',
    styleUrls: ['./settings-dialog.component.scss']
})
export class SettingsDialogComponent implements OnInit {

    constructor(private service: SystemDescriptionService) {
    }

    ngOnInit(): void {
    }

    @Input()
    spec: SettingsDialogSpec

    specWrapper: SettingsDialogSpecWrapper

    ngOnChanges(changes: SimpleChanges): void {
        if (this.spec != null) {
            this.specWrapper = new SettingsDialogSpecWrapper(this.spec)
            this.configureSettingsControlForSystemType(this.specWrapper.getSystemType(this.spec.data.systemComponentKey))
            this.formGroup.setValue(this.spec.data)
            this.formGroup.markAsPristine()
            this.formGroup.markAsUntouched()
            this.connectionTester = null
        }
    }

    formGroup: FormGroup = new FormGroup({
        environmentKey: new FormControl(null, [Validators.required]),
        systemComponentKey: new FormControl(null, [Validators.required]),
        settings: new FormArray([])
    })


    environmentKeyControl(): AbstractControl {
        return this.formGroup.get('environmentKey')
    }

    onEnvironmentChange() {

    }

    environmentKeyControlInvalid() {
        return showValidationMessages(this.environmentKeyControl())
    }

    systemComponentKeyControl(): AbstractControl {
        return this.formGroup.get('systemComponentKey')
    }

    onSystemComponentKeyChange() {
        let systemComponentKey = this.systemComponentKeyControl().value;
        let systemType = this.specWrapper.getSystemType(systemComponentKey)
        this.configureSettingsControlForSystemType(systemType);
    }

    systemComponentKeyControlInvalid() {
      return showValidationMessages(this.systemComponentKeyControl())
    }

    settingsControl(): FormArray {
        return <FormArray>this.formGroup.get('settings')
    }

    settingValueControl(i: number): AbstractControl {
        return this.settingsControl().controls[i].get('value')
    }

    settingValueControlInvalid(i: number): boolean {
        return showValidationMessages(this.settingValueControl(i))
    }

    settingDescriptors: SettingDescriptor[] = []

    configureSettingsControlForSystemType(systemType: string) {
        this.settingDescriptors = this.service.getSettingDescriptors(systemType)
        this.settingsControl().controls = []
        this.settingDescriptors.forEach(descriptor =>
            this.settingsControl().push(new FormGroup({
                key: new FormControl(descriptor.key, [Validators.required]),
                value: new FormControl(null, [Validators.required])
            }))
        )
    }

    cancel() {
        this.spec.cancel()
    }

    confirm() {
        this.spec.confirm(this.formGroup.value)
    }

    // connection test

    faBolt = faBolt
    faCheckCircle = faCheckCircle
    connectionTester: ConnectionTester

    runTest() {
        let settingObject = {}
        this.settingsControl().value.forEach(setting => {
            settingObject[setting.key] = setting.value
        })

        this.connectionTester = new ConnectionTester(this.service)
        this.connectionTester.run(settingObject)

    }

    cancelTest() {
        this.connectionTester = null
    }
}
