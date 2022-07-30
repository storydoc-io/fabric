import {Injectable, OnDestroy} from '@angular/core';
import {SystemDescriptionDto} from "@fabric/models";
import {SystemDescriptionControllerService} from "@fabric/services";
import {distinctUntilChanged, map} from "rxjs/operators";
import {BehaviorSubject, Subscription} from "rxjs";
import {logChangesToObservable} from "@fabric/common";

interface SystemDescriptionState {
    systemDescription: SystemDescriptionDto
}

@Injectable({
    providedIn: 'root'
})
export class SystemDescriptionService implements OnDestroy {

    constructor(private systemDescriptionControllerService: SystemDescriptionControllerService) {
        this.init()
    }

    private store = new BehaviorSubject<SystemDescriptionState>({systemDescription: null})

    systemDescription$ = this.store.pipe(
        map(state => state.systemDescription),
        distinctUntilChanged(),
    )

    private subscriptions: Subscription[] = []

    private init() {
        this.subscriptions.push(logChangesToObservable('systemDescriptionStore::systemDescription$ >>', this.systemDescription$))
        this.loadSystemDescription()
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe())
    }

    loadSystemDescription() {
        this.systemDescriptionControllerService.getSystemDescriptionUsingGet({}).subscribe(dto => {
            this.store.next({systemDescription: dto})
        })
    }


}
