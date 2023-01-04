import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";
import {ActivatedRoute} from "@angular/router";
import {SystemDescriptionService, SystemDescriptionWrapper} from "../system-description.service";
import {SystemDescriptionDto} from "@fabric/models";
import {RoutingService} from "../../common/routing.service";

@Component({
    selector: 'app-meta-model-page',
    templateUrl: './meta-model-page.component.html',
    styleUrls: ['./meta-model-page.component.scss']
})
export class MetaModelPageComponent implements OnInit {

    constructor(private activatedRoute: ActivatedRoute, private systemDescriptionService: SystemDescriptionService, private routingService: RoutingService) {
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
          {
              label: this.systemComponentKey,
              route: this.routingService.dataSourcesPageRouteWithSelection(this.systemComponentKey)
          },
          {
              label: 'MetaModel'
          },
      ]
    }

    systemComponentKey: string

    systemDescription$ = this.systemDescriptionService.systemDescription$

    ngOnInit(): void {
        this.activatedRoute.paramMap.subscribe((params) => {
            this.systemComponentKey = params.get('systemComponentKey')
            this.systemDescriptionService.loadSystemDescription()
        })
    }

    systemComponent(systemDescription: SystemDescriptionDto, systemComponentKey: string) {
        return new SystemDescriptionWrapper(systemDescription).getSystemComponentByKey(systemComponentKey)
    }
}
