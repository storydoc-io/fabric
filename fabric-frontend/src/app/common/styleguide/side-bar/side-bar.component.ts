import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {SideBarService} from "./side-bar.service";
import {faBars, faTimes} from '@fortawesome/free-solid-svg-icons';

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
export class SideBarComponent implements OnInit {

  actions: ActionSpec[] = [
    {
      id : 'settings',
      label: 'Settings',
      route: ['fe', 'systemdescription']
    },
    {
      id : 'dashboard',
      label: 'Snapshots',
      route: ['fe', 'dashboard']
    },
    {
      id : 'console',
      label: 'Console',
      route: ['fe', 'console']
    },
    {
      id : 'navigation',
      label: 'Navigator',
      route: ['fe', 'navigation']
    },
  ]

  @Input()
  active: PageId;

  navigateTo(route:string[]) {
    this.router.navigate(route )
  }

  constructor(private router: Router, private service: SideBarService) { }

  ngOnInit(): void {
  }

  toggleCollapse() {
    this.service.toggleState()
  }

  collapsed(): boolean {
    return this.service.collapsed;
  }

  icon() {
    return this.collapsed() ? faBars : faTimes
  }

}
