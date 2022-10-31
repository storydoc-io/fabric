import {Component, OnInit} from '@angular/core';
import {SystemDescriptionService} from "./system-description.service";
import {SystemComponentDto} from "@fabric/models";
import {BreadcrumbItem} from "@fabric/common";
import {NavItemSpec, NavSpec} from "../common/styleguide/nav/nav.component";

type SystemDescriptionTabState = 'ENVIRONMENTS' | 'SYSTEM'
type DataSourceTabState = 'SETTINGS' | 'METADATA'

@Component({
    selector: 'app-system-description-page',
    templateUrl: './system-description-page.component.html',
    styleUrls: ['./system-description-page.component.scss']
})
export class SystemDescriptionPageComponent implements OnInit {

    breadcrumbs: BreadcrumbItem[] = [
        {
            label: 'Home',
            route: '/'
        },
        {
            label: 'Settings'
        }
    ]

    constructor(private service: SystemDescriptionService) {}

    systemDescription$ = this.service.systemDescription$

    systemTypeDescriptors$ = this.service.systemTypeDescriptors$

    leftNavigation: NavSpec = {
        defaultSelection: 'SYSTEM',
        select: (key) => this.selectedSystemDescriptionTab = key,
        items: [
            <NavItemSpec>{
                label: 'Datasources',
                key: 'SYSTEM'
            },
            <NavItemSpec>{
                label: 'Environments',
                key: 'ENVIRONMENTS'
            }
        ]
    }
    selectedSystemDescriptionTab: SystemDescriptionTabState = 'SYSTEM'

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
