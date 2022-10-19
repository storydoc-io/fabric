import {Component, Input, TemplateRef} from '@angular/core';
import {SideBarService} from "../side-bar/side-bar.service";

@Component({
  selector: 'app-layout-1col',
  templateUrl: './layout-1col.component.html',
  styleUrls: ['./layout-1col.component.scss']
})
export class Layout1ColComponent {

  constructor(private sideBarService: SideBarService) {}

  @Input()
  sidebar: TemplateRef<any>

  @Input()
  breadcrumb: TemplateRef<any>

  @Input()
  title: TemplateRef<any>

  @Input()
  middle: TemplateRef<any>

  sideBarCollapsed(): boolean {
    return this.sideBarService.collapsed
  }

}
