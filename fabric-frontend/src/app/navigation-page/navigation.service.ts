import {Injectable} from '@angular/core';
import {NavItem} from "@fabric/models";
import {ConsoleControllerService} from "@fabric/services";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  constructor(private consoleControllerService : ConsoleControllerService) { }

  loadNavItems(systemComponentKey: string): Promise<NavItem[]>  {
    return this.consoleControllerService.getNavigationUsingPost({body: {
        systemComponentKey,
      }}).toPromise()
  }
}
