import {Component, OnInit} from '@angular/core';
import {BreadcrumbItem} from "@fabric/common";

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './snapshots-page.component.html',
  styleUrls: ['./snapshots-page.component.scss']
})
export class SnapshotsPageComponent implements OnInit {

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
