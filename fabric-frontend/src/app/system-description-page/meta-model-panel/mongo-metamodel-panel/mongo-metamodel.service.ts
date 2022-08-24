import {Injectable, OnDestroy} from '@angular/core';
import {MongoSnapshotControllerService} from "@fabric/services";
import {MongoMetaModel, MongoNavigationModel, MongoSnapshot} from "@fabric/models";
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {logChangesToObservable} from "@fabric/common";


interface MongoMetaModelStoreState {
  modelMap: Map<String, MongoMetaModel>
}

@Injectable({
  providedIn: 'root'
})
export class MongoMetaModelService implements  OnDestroy{

  constructor(private mongoSnapshotControllerService: MongoSnapshotControllerService) { this.init() }

  private store = new BehaviorSubject<MongoMetaModelStoreState>({ modelMap: new Map() })

  metaModels$ = this.store.pipe(
      map(state => state.modelMap),
      distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  private init() {
    this.subscriptions.push(logChangesToObservable('store::metaModels$ >>', this.metaModels$))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  public load(systemComponentKey: string) {
    this.mongoSnapshotControllerService.getMetaModelUsingGet({systemComponentKey}).subscribe(dto => {
      let map : Map<String, MongoMetaModel> = this.store.getValue().modelMap
      map.set(systemComponentKey, dto)
      this.store.next({ modelMap : new Map<String, MongoMetaModel>(map) })
    })
  }


}
