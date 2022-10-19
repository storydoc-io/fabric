import {Component, Input, OnInit} from '@angular/core';
import {NavTreeItemDto} from "../navigation-tree/navigation-tree.component";
import {faArrowRight} from "@fortawesome/free-solid-svg-icons";

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

  @Input()
  depth: number = 0

  ngOnInit(): void {
  }

}
