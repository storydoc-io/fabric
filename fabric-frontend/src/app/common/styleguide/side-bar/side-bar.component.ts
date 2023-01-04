import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {SideBarService} from "./side-bar.service";
import {RoutingService} from "../../routing.service";

interface ActionSpec {
    label: string
    id?: PageId
    route?: string[]
    level: number
}

type PageId = 'settings' | 'environments'| 'datasources' | 'dashboard' | 'console' | 'navigation'

@Component({
    selector: 'app-side-bar',
    templateUrl: './side-bar.component.html',
    styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent {

    constructor(private router: Router, private sideBarService: SideBarService, private routingService: RoutingService) {
    }

    @Input()
    active: PageId;

    actions: ActionSpec[] = [
        {
            id: 'settings',
            label: 'Settings',
            level: 0
        },
        {
            id: 'environments',
            label: 'Environments',
            route: this.routingService.environmentsPageRoute(),
            level: 1
        },
        {
            id: 'datasources',
            label: 'Datasources',
            route: this.routingService.dataSourcesPageRoute(),
            level: 1
        },
        {
            id: 'console',
            label: 'Console',
            route: this.routingService.consolePageRoute(),
            level: 0
        },

    ]

    navigateTo(route: string[]) {
        this.sideBarService.collapse()
        this.router.navigate(route)
    }

    toggleCollapse() {
        this.sideBarService.toggleState()
    }

    collapsed(): boolean {
        return this.sideBarService.collapsed;
    }

}
