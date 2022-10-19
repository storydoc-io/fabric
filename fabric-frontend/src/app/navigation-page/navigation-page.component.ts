import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";
import {DataSourceSelection} from "@fabric/component";

@Component({
  selector: 'app-navigation-page',
  templateUrl: './navigation-page.component.html',
  styleUrls: ['./navigation-page.component.scss']
})
export class NavigationPageComponent implements OnInit {

  breadcrumbs: BreadcrumbItem[] = [
    {
      label: 'Home',
      route: '/'
    },
    {
      label: 'Navigator'
    }
  ]


  constructor() { }

  ngOnInit(): void {
  }

  // datasource selection

  selectedDataSource: DataSourceSelection

  selectDataSource(dataSource: DataSourceSelection) {
    this.selectedDataSource = dataSource
  }



}
