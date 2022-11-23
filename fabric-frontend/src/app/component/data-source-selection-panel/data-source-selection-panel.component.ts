import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SystemDescriptionService, SystemDescriptionWrapper} from "../../system-description-page/system-description.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {faBolt, faTimes} from '@fortawesome/free-solid-svg-icons';
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";

export interface DataSourceSelection {
    environment: EnvironmentDto,
    systemComponent: SystemComponentDto
}

@Component({
    selector: 'app-data-source-selection-panel',
    templateUrl: './data-source-selection-panel.component.html',
    styleUrls: ['./data-source-selection-panel.component.scss']
})
export class DataSourceSelectionPanelComponent implements OnInit {

    constructor(private systemDescriptionService: SystemDescriptionService) {
    }

    systemDescription$ = this.systemDescriptionService.systemDescription$;

    connected: boolean = false;

    faBolt = faBolt
    faTimes = faTimes

    ngOnInit(): void {
    }

    @Input()
    systemDescription: SystemDescriptionDto

    @Output()
    selected = new EventEmitter<DataSourceSelection>()

    formGroup: FormGroup = new FormGroup({
        environment: new FormControl(null, [Validators.required]),
        systemComponent: new FormControl(null, [Validators.required]),
    })

    get environmentControl(): FormControl {
        return <FormControl> this.formGroup.get('environment')
    }

    getAvailableEnvironments(systemDescription: SystemDescriptionDto): EnvironmentDto[] {
        let systemComponentKey = this.systemComponentControl.value?.key
        if (!systemComponentKey) return []
        return new SystemDescriptionWrapper(systemDescription).getEnvironmentsWithSettingsForSystemComponentKey(systemComponentKey)
    }

    get systemComponentControl(): FormControl {
        return <FormControl> this.formGroup.get('systemComponent')
    }

    selectedSystemComponent() {
        return this.systemComponentControl.value;
    }


    connect() {
        let value = this.formGroup.value;
        console.log('value:', value)
        this.selected.emit(value)
        this.connected = true
    }

    disconnect() {
        this.selected.emit(null)
        this.connected = false
        this.formGroup.setValue({ environmentKey: null, systemComponentKey : null })
    }

}
