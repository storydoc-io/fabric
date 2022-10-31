import {Component, Input, OnInit, SimpleChanges} from '@angular/core';
import {DataSourceSelection} from "@fabric/component";
import {NavItem} from "@fabric/models";
import {NavigationService} from "../navigation.service";

@Component({
  selector: 'app-navigation-panel',
  templateUrl: './navigation-panel.component.html',
  styleUrls: ['./navigation-panel.component.scss']
})
export class NavigationPanelComponent implements OnInit {

  constructor(private service: NavigationService) { }

  ngOnInit(): void {
  }

  @Input()
  dataSource: DataSourceSelection

  ngOnChanges(changes: SimpleChanges) {
    let systemComponentKey = this.dataSource.systemComponentKey;
    this.service.loadNavItems(systemComponentKey, null)
  }

  tree$ = this.service.tree$

  // navitems

  navItems: NavItem[]
  currentNavItem: NavItem

  private initNavItems(navItems: NavItem[]) {
    return this.navItems = navItems;
  }

  navItemSelected(navItem: NavItem) {
    this.currentNavItem = navItem

  }

}
