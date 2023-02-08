import {Injectable} from '@angular/core';
import {MetaModelControllerService} from "@fabric/services";
import {EntityDto, SystemComponentDto} from "@fabric/models";
import {BehaviorSubject, Subscription} from "rxjs";
import {logChangesToObservable} from "@fabric/common";

interface MetaModelState {
    metaModel: EntityDto,
    fetching: boolean
}

@Injectable({
    providedIn: 'root'
})
export class MetaDataService {

    private store = new BehaviorSubject<MetaModelState>({metaModel: null, fetching: false})

    metaModel$ = this.store


    constructor(private metaModelControllerService: MetaModelControllerService) {
        this.init()
    }

    private subscriptions: Subscription[] = []

    private init() {
        this.subscriptions.push(logChangesToObservable('store::metaModel$ >>', this.metaModel$))
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe())
    }

    public loadMetaModel(envKey: string, systemComponentKey: string) {
        return this.metaModelControllerService.getMetaModelAsEntityUsingGet({envKey, systemComponentKey}).subscribe(entityDto => {
            this.store.next({metaModel: entityDto, fetching: false})
        })
    }

    public fetchMetaModel(systemComponent: SystemComponentDto, environmentKey: string) {
        this.store.next({metaModel: null, fetching: true})
        return this.metaModelControllerService.createMetaModelUsingPost({
            environmentKey,
            systemComponentKey: systemComponent.key
        }).subscribe(result => {
            this.loadMetaModel(environmentKey, systemComponent.key)
        })
    }


}
