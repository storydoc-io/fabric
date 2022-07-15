import { Injectable } from '@angular/core';
import {SnapshotControllerService} from "@fabric/services";
import {SnapshotDto} from "@fabric/models";
import {BehaviorSubject} from "rxjs";
import {map,distinctUntilChanged} from "rxjs/operators";

interface SnapshotStoreState {
  snapshot: SnapshotDto
}

@Injectable()
export class SnapshotService {

  constructor(private snapshotControllerService : SnapshotControllerService) { this.init() }

  private store = new BehaviorSubject<SnapshotStoreState>({ snapshot: null})

  snapshot$ = this.store.pipe(
      map(state => state.snapshot),
      distinctUntilChanged(),
  )

  init() {
    this.snapshot$.subscribe((dto)=> console.log(">> snapshot$", dto))
  }

  loadSnapshot(id: string) {
    this.snapshotControllerService.getByIdUsingGet({}).subscribe(dto => {
      this.store.next({ snapshot: dto })
    })
  }

}
