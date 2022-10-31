import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SideBarService {

  constructor() { }

  collapsed: boolean = true

  toggleState() {
    this.collapsed = !this.collapsed
  }

  collapse() {
    this.collapsed = true
  }

}
