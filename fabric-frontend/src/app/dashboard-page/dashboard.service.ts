import {Injectable, OnDestroy} from '@angular/core';
import {SnapshotControllerService} from "@fabric/services";
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {ExecutionId, SnapshotId, SnapshotSummaryDto} from "@fabric/models";

interface DashboardStoreState {
    summaries: SnapshotSummaryDto[]
}

@Injectable({
    providedIn: 'root'
})
export class DashboardService implements OnDestroy {

    constructor(private snapshotControllerService: SnapshotControllerService) {
        this.init()
    }

    private store = new BehaviorSubject<DashboardStoreState>({ summaries: []})

    summaries$ = this.store.pipe(
        map(state => state.summaries),
        distinctUntilChanged(),
    )

    private subscriptions: Subscription[] = []

    init() {
        this.loadSummaries();
    }

    ngOnDestroy(): void {
        this.subscriptions.forEach(s => s.unsubscribe())
    }

    private loadSummaries() {
        this.snapshotControllerService.listUsingGet({}).subscribe(summaries => {
            this.store.next({summaries: summaries})
        })
    }

    createSnapshot(environment: string, name: string) {
        this.snapshotControllerService.createUsingPost({ environment, name}).subscribe(
        (dto) => this.loadSummaries()
        )
    }

    delete(snapshotId: SnapshotId) {
        this.snapshotControllerService.deleteByIdUsingDelete({id: snapshotId.id}).subscribe(()=> {
            this.loadSummaries()
        })

    }

    upload(snapshotId: SnapshotId, envKey: string): Promise<ExecutionId> {
        return this.snapshotControllerService.uploadUsingPost({
            environmentKey: envKey,
            id: snapshotId.id
        }).toPromise()
    }
}
