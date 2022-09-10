import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SystemDescriptionService} from "../../system-description-page/system-description.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {faBolt} from '@fortawesome/free-solid-svg-icons';

export interface DataSourceSelection {
    environmentKey: string,
    systemComponentKey: string
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

    collapsed: boolean = false

    faBolt = faBolt

    ngOnInit(): void {
    }

    @Output()
    selected = new EventEmitter<DataSourceSelection>()

    formGroup: FormGroup = new FormGroup({
        environmentKey: new FormControl(null, [Validators.required]),
        systemComponentKey: new FormControl(null, [Validators.required]),
    })

    onEnvironmentChange() {

    }

    connect() {
        this.selected.emit(this.formGroup.value)
        this.collapse()
    }

    collapse() {
        this.collapsed = true
    }

    expand() {
        this.collapsed = false
    }
}
