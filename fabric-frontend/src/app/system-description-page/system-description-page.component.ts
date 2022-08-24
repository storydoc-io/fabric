import {Component, OnInit} from '@angular/core';
import {SystemDescriptionService} from "./system-description.service";
import {SystemComponentDto} from "@fabric/models";

type SystemDescriptionTabState = 'ENVIRONMENTS' | 'SYSTEM'
type DataSourceTabState = 'SETTINGS' | 'METADATA'

@Component({
    selector: 'app-system-description-page',
    templateUrl: './system-description-page.component.html',
    styleUrls: ['./system-description-page.component.scss']
})
export class SystemDescriptionPageComponent implements OnInit {

    constructor(private service: SystemDescriptionService) {}

    systemDescription$ = this.service.systemDescription$

    selectedSystemDescriptionTab: SystemDescriptionTabState = 'ENVIRONMENTS'

    selectedDataSourceTab: DataSourceTabState = 'SETTINGS'

    ngOnInit(): void {
    }

    selectSystemDescriptionTab(tab: SystemDescriptionTabState) {
        this.selectedSystemDescriptionTab = tab
    }

    selectDataSourceTab(tab: DataSourceTabState) {
        this.selectedDataSourceTab = tab
    }

    selectedSystemComponent: SystemComponentDto

    systemComponentSelectionChanged(systemComponent: SystemComponentDto) {
        this.selectedSystemComponent = systemComponent
    }

}
