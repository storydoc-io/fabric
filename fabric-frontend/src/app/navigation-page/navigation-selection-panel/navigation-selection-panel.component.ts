import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavItem} from "@fabric/models";

@Component({
  selector: 'app-navigation-selection-panel',
  templateUrl: './navigation-selection-panel.component.html',
  styleUrls: ['./navigation-selection-panel.component.scss']
})
export class NavigationSelectionPanelComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  items: NavItem[]

  @Output()
  selected = new EventEmitter<NavItem>()

  apply(item: NavItem) {
    this.selected.emit(item)
  }

}
