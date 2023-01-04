import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";
import {SystemDescriptionDto} from "@fabric/models";
import {SystemDescriptionService, SystemDescriptionWrapper} from "../system-description.service";
import {ActivatedRoute} from "@angular/router";
import {RoutingService} from "../../common/routing.service";

@Component({
  selector: 'app-settings-page',
  templateUrl: './settings-page.component.html',
  styleUrls: ['./settings-page.component.scss']
})
export class SettingsPageComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private systemDescriptionService: SystemDescriptionService, private routingService : RoutingService) {}

  get breadcrumbs() : BreadcrumbItem[] {
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
        label: 'Connection Settings'
      }
    ]
  }

  systemComponentKey: string

  systemDescription$ = this.systemDescriptionService.systemDescription$

  systemTypeDescriptors$ = this.systemDescriptionService.systemTypeDescriptors$

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.systemComponentKey = params.get('systemComponentKey')
      this.systemDescriptionService.loadSystemDescription()
      this.systemDescriptionService.loadSystemTypeDescriptors()
    })
  }

  systemComponent(systemDescription: SystemDescriptionDto, systemComponentKey: string) {
    return new SystemDescriptionWrapper(systemDescription).getSystemComponentByKey(systemComponentKey)
  }

}
