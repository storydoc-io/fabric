import { Component, OnInit } from '@angular/core';
import {BreadcrumbItem} from "../common/layout/breadcrumb/breadcrumb.component";
import {DataSourceSelection} from "./data-source-selection-panel/data-source-selection-panel.component";

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

  constructor() { }

  ngOnInit(): void {
  }

  selectedDataSource: DataSourceSelection

  selectDataSource(dataSource: DataSourceSelection) {
    this.selectedDataSource = dataSource
  }

}
