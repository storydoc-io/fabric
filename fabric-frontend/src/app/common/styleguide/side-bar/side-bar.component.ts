import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";

export interface ActionSpec {
  label: string
  id : PageId
  route: string[]
}

type PageId = 'settings' | 'dashboard' | 'console'

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
  ]

  @Input()
  active: PageId;

  navigateTo(route:string[]) {
    this.router.navigate(route )
  }

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

}
