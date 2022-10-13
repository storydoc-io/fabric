import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SideBarService {

  constructor() { }

  collapsed: boolean = false

  toggleState() {
    this.collapsed = !this.collapsed
  }

}
