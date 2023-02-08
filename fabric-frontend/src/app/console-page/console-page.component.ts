import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";
import {DataSourceSelection} from "@fabric/component";
import {HealthCheckService} from "../common/health-check.service";
import {SystemDescriptionService} from "../settings/system-description.service";
import {RoutingService} from "../common/routing.service";

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
        public routingService: RoutingService,
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
