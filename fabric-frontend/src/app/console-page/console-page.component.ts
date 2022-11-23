import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";
import {DataSourceSelection} from "@fabric/component";
import {HealthCheckService} from "../common/health-check.service";
import {SystemDescriptionService} from "../system-description-page/system-description.service";
import {NavigationService} from "../navigation-page/navigation.service";

@Component({
    selector: 'app-console-page',
    templateUrl: './console-page.component.html',
    styleUrls: ['./console-page.component.scss']
})
export class ConsolePageComponent implements OnInit {

    breadcrumbs: BreadcrumbItem[] = [
        {
            label: 'Home',
            route: '/'
        },
        {
            label: 'Console'
        }
    ]

    constructor(
        public goto: NavigationService,
        private healthCheckService: HealthCheckService,
        private systemDescriptionService: SystemDescriptionService) {
    }

    ngOnInit(): void {
    }

    systemDescription$ = this.systemDescriptionService.systemDescription$

    selectedDataSource: DataSourceSelection

    selectDataSource(dataSource: DataSourceSelection) {
        this.selectedDataSource = dataSource
    }

}
