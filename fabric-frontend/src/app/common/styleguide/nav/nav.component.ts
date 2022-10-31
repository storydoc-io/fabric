import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {faAngleRight} from "@fortawesome/free-solid-svg-icons";

export interface NavSpec {
  items: NavItemSpec[]
  select: (string) => void
  defaultSelection: string
}

export interface NavItemSpec {
  label: string
  key: string
}

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnChanges{

  faAngleRight = faAngleRight

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    this.doSelect(this.spec.defaultSelection)
  }

  active: string

  @Input()
  spec: NavSpec

  doSelect(key: string) {
    this.active = key
    this.spec.select(key)
  }

}
