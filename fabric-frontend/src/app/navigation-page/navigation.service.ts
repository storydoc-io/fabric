import {Injectable} from '@angular/core';
import {NavItem} from "@fabric/models";
import {ConsoleControllerService} from "@fabric/services";
import {BehaviorSubject} from "rxjs";
import {distinctUntilChanged} from "rxjs/operators";
import {NavTreeItemDto} from "./navigation-tree/navigation-tree.component";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private consoleControllerService : ConsoleControllerService) { }

  private tree = new BehaviorSubject<NavTreeItemDto>(null)

  tree$ = this.tree.pipe(
      distinctUntilChanged(),
  )

  loadNavItems(systemComponentKey: string, navItem: NavItem) {
    return this.consoleControllerService.getNavigationUsingPost({body: {
        systemComponentKey,
      }}).subscribe(navItems => {
        if (!navItem) {
          this.tree.next({
            root: true,
            navItems,
            children: [
              {
                label: 'test',
                root: false,
                columns: ['AAAAAAAAAAAAAAAAA', 'BBBBBBBBBBBBBBBB', 'CCCCCCCCCCCCCCCCCCCC'],
                rows: [],
                navItems: navItems
              }
            ]
          })
        }
    })
  }
}
