import { Injectable } from '@angular/core';
import {MongoSnapshotControllerService} from "@fabric/services";
import {MongoNavigationModel, MongoSnapshot} from "@fabric/models";
import {BehaviorSubject} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";


interface MongoNavigationModelStoreState {
  navigationModelMap: Map<String, MongoNavigationModel>
}

@Injectable({
  providedIn: 'root'
})
export class MongoNavigationModelService {

  constructor(private mongoSnapshotControllerService: MongoSnapshotControllerService) { }

  private store = new BehaviorSubject<MongoNavigationModelStoreState>({ navigationModelMap: new Map() })

  navigationModels$ = this.store.pipe(
      map(state => state.navigationModelMap),
      distinctUntilChanged(),
  )

  public load(systemComponentKey: string) {
    this.mongoSnapshotControllerService.getNavigationModelUsingGet({systemComponentKey}).subscribe(dto => {
      let map : Map<String, MongoNavigationModel> = this.store.getValue().navigationModelMap
      map.set(systemComponentKey, dto)
      this.store.next({ navigationModelMap : new Map<String, MongoNavigationModel>(map) })
    })
  }


}
