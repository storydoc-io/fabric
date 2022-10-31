import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NavTreeItemDto} from "../navigation-tree/navigation-tree.component";
import {faArrowRight} from "@fortawesome/free-solid-svg-icons";
import {NavItem} from "@fabric/models";

@Component({
  selector: 'app-navigation-tree-item',
  templateUrl: './navigation-tree-item.component.html',
  styleUrls: ['./navigation-tree-item.component.scss']
})
export class NavigationTreeItemComponent implements OnInit {

  selectIcon = faArrowRight

  constructor() { }

  @Input()
  item: NavTreeItemDto

  @Output()
  onNavSelect = new EventEmitter<NavItem>()

  @Input()
  depth: number = 0

  ngOnInit(): void {
  }

  selectNav(item: NavItem) {
    console.log('selected: ', item)
    this.onNavSelect.emit(item)
  }

}
