import {Injectable} from '@angular/core';
import {MongoSnapshotControllerService} from "@fabric/services";
import {MongoSnapshot, SnapshotId} from "@fabric/models";
import {BehaviorSubject} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";


interface MongoSnapshotStoreState {
  mongoSnapshot: MongoSnapshot
}

@Injectable({
  providedIn: 'root'
})
export class MongoService {

  constructor(private mongoSnapshotControllerService: MongoSnapshotControllerService) { }

  private store = new BehaviorSubject<MongoSnapshotStoreState>({ mongoSnapshot: null})

  mongoSnapshot$ = this.store.pipe(
      map(state => state.mongoSnapshot),
      distinctUntilChanged(),
  )

  public load(snapshotId: SnapshotId, componentKey: string) {
    this.mongoSnapshotControllerService.getMongoSnapshotUsingGet({ id: snapshotId.id, componentKey}).subscribe(mongoSnapshot =>
      this.store.next({ mongoSnapshot })
    )
  }

}
