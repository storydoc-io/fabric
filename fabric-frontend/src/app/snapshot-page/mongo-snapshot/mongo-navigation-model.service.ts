import {Injectable, OnDestroy} from '@angular/core';
import {MongoSnapshotControllerService} from "@fabric/services";
import {MongoNavigationModel, MongoSnapshot} from "@fabric/models";
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {logChangesToObservable} from "@fabric/common";


interface MongoNavigationModelStoreState {
  navigationModelMap: Map<String, MongoNavigationModel>
}

@Injectable({
  providedIn: 'root'
})
export class MongoNavigationModelService implements  OnDestroy{

  constructor(private mongoSnapshotControllerService: MongoSnapshotControllerService) { this.init() }

  private store = new BehaviorSubject<MongoNavigationModelStoreState>({ navigationModelMap: new Map() })

  navigationModels$ = this.store.pipe(
      map(state => state.navigationModelMap),
      distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  private init() {
    this.subscriptions.push(logChangesToObservable('store::navigationModels$ >>', this.navigationModels$))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  public load(systemComponentKey: string) {
    this.mongoSnapshotControllerService.getNavigationModelUsingGet({systemComponentKey}).subscribe(dto => {
      let map : Map<String, MongoNavigationModel> = this.store.getValue().navigationModelMap
      map.set(systemComponentKey, dto)
      this.store.next({ navigationModelMap : new Map<String, MongoNavigationModel>(map) })
    })
  }


}
