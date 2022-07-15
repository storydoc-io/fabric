import {Injectable} from '@angular/core';
import {SnapshotControllerService} from "@fabric/services";

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    constructor(private snapshotControllerService: SnapshotControllerService) {
        this.init()
    }

    init() {
        console.log('init()')
        this.snapshotControllerService.listUsingGet({}).subscribe(dto => {
            console.log('received: ', dto)
        })
    }

    createSnapshot() {
        this.snapshotControllerService.createUsingPost({}).subscribe((dto) => console.log('received: ', dto))
    }
}
