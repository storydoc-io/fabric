import {Component, OnInit} from '@angular/core';
import {SystemDescriptionService} from "../system-description.service";
import {RoutingService} from "../../common/routing.service";
import {BreadcrumbItem} from "@fabric/common";

@Component({
  selector: 'app-environments-page',
  templateUrl: './environments-page.component.html',
  styleUrls: ['./environments-page.component.scss']
})
export class EnvironmentsPageComponent implements OnInit {

  constructor(private systemDescriptionService: SystemDescriptionService, private routingService : RoutingService) {}

  ngOnInit(): void {
    this.systemDescriptionService.loadSystemDescription()
    this.systemDescriptionService.loadSystemTypeDescriptors()
  }

  get breadcrumbs() : BreadcrumbItem[] {
    return [
      {
        label: 'Home',
        route: this.routingService.homeRoute()
      },
      {
        label: 'Environments',
        route: this.routingService.environmentsPageRoute()
      },
    ]
  }

  systemDescription$ = this.systemDescriptionService.systemDescription$

  systemTypeDescriptors$ = this.systemDescriptionService.systemTypeDescriptors$

}
