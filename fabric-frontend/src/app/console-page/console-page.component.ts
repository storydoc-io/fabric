import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";
import {DataSourceSelection} from "@fabric/component";
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

  constructor(public goto: RoutingService) { }

  ngOnInit(): void {
  }

  selectedDataSource: DataSourceSelection

  selectDataSource(dataSource: DataSourceSelection) {
    this.selectedDataSource = dataSource
  }

}
