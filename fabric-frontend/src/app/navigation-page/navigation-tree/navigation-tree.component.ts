import {Component, Input, OnInit} from '@angular/core';
import {NavItem, Row} from "@fabric/models";

export interface NavTreeItemDto {
    root: boolean
    navItems: NavItem[]
    label?: string
    rows?: Row[]
    columns?: string[]
    children?: NavTreeItemDto[]
}

@Component({
  selector: 'app-navigation-tree',
  templateUrl: './navigation-tree.component.html',
  styleUrls: ['./navigation-tree.component.scss']
})
export class NavigationTreeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  tree: NavTreeItemDto

}
