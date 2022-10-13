import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MetaNavItem} from "@fabric/models";

@Component({
  selector: 'app-navigation-panel',
  templateUrl: './navigation-panel.component.html',
  styleUrls: ['./navigation-panel.component.scss']
})
export class NavigationPanelComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  items: MetaNavItem[]

  @Output()
  selected = new EventEmitter<MetaNavItem>()

  apply(item: MetaNavItem) {
    this.selected.emit(item)
  }
}
