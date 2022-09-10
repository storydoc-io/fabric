import {Component, Input, OnInit} from '@angular/core';

export interface BreadcrumbItem {
  label?: string
  route?: string
}

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent implements OnInit {

  constructor() { }

  @Input()
  breadcrumbs: BreadcrumbItem[]

  ngOnInit(): void {
  }

  get active(): BreadcrumbItem {
    let idx = this.breadcrumbs.length-1
    return this.breadcrumbs[idx]
  }

  get parentOfActive(): BreadcrumbItem {
    let idx = this.breadcrumbs.length-2
    return this.breadcrumbs[idx]
  }

  get allParents(): BreadcrumbItem[] {
    let idxActive = this.breadcrumbs.length-1
    return this.breadcrumbs.slice(0, idxActive)
  }



}
