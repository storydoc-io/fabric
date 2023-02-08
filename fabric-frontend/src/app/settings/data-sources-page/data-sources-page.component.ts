import {Component, OnInit} from '@angular/core';
import {SystemDescriptionService} from "../system-description.service";
import {BreadcrumbItem} from "@fabric/common";
import {RoutingService} from "../../common/routing.service";
import {SystemComponentDto} from "@fabric/models";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'app-data-sources-page',
    templateUrl: './data-sources-page.component.html',
    styleUrls: ['./data-sources-page.component.scss']
})
export class DataSourcesPageComponent implements OnInit {

    constructor(private activatedRoute: ActivatedRoute, private systemDescriptionService: SystemDescriptionService, private routingService: RoutingService) {
    }

    systemComponentKey: string

    ngOnInit(): void {
        this.systemDescriptionService.loadSystemDescription()
        this.systemDescriptionService.loadSystemTypeDescriptors()
        this.activatedRoute.paramMap.subscribe((params) => {
            this.systemComponentKey = params.get('systemComponentKey')
        })
    }

    get breadcrumbs(): BreadcrumbItem[] {
        return [
            {
                label: 'Home',
                route: this.routingService.homeRoute()
            },
            {
                label: 'Datasources',
                route: this.routingService.dataSourcesPageRoute()
            },
        ]
    }

    systemDescription$ = this.systemDescriptionService.systemDescription$

    systemTypeDescriptors$ = this.systemDescriptionService.systemTypeDescriptors$

    selectedSystemComponent: SystemComponentDto

    systemComponentSelectionChanged(systemComponent: SystemComponentDto) {
        this.selectedSystemComponent = systemComponent
    }

}
