import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {SideBarService} from "./side-bar.service";

export interface ActionSpec {
  label: string
  id : PageId
  route: string[]
}

type PageId = 'settings' | 'dashboard' | 'console' | 'navigation'

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent {

  constructor(private router: Router, private sideBarService: SideBarService) { }

  @Input()
  active: PageId;

  actions: ActionSpec[] = [
    {
      id : 'settings',
      label: 'Settings',
      route: ['fe', 'systemdescription']
    },
    {
      id : 'console',
      label: 'Console',
      route: ['fe', 'console']
    },
  ]

  navigateTo(route:string[]) {
    this.sideBarService.collapse()
    this.router.navigate(route )
  }

  toggleCollapse() {
    this.sideBarService.toggleState()
  }

  collapsed(): boolean {
    return this.sideBarService.collapsed;
  }

}
