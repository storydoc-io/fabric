import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss']
})
export class DashboardPageComponent implements OnInit {

  breadcrumbs: BreadcrumbItem[] = [
    {
      label: 'Home',
      route: '/'
    },
    {
      label: 'Snapshots'
    }
  ]

  constructor() { }

  ngOnInit(): void {
  }



}
