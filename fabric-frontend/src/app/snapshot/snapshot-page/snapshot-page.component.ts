import {Component, OnInit} from '@angular/core';
import {SnapshotService} from "./snapshot.service";
import {ActivatedRoute} from "@angular/router";
import {SnapshotDto, SnapshotId} from "@fabric/models";
import {BreadcrumbItem} from "@fabric/common";

@Component({
  selector: 'app-snapshot-page',
  templateUrl: './snapshot-page.component.html',
  styleUrls: ['./snapshot-page.component.scss'],
  providers: [SnapshotService]
})
export class SnapshotPageComponent implements OnInit {

  constructor(
      private route: ActivatedRoute,
      private service: SnapshotService) {
  }

  snapshotId: SnapshotId

  snapshot$ = this.service.snapshot$

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.snapshotId = { id : params.get('snapshotId') }
      this.service.loadSnapshot(this.snapshotId)
    })
  }

    breadcrumbs(snapshot: SnapshotDto): BreadcrumbItem[] {
        return  [
        {
          label: 'Home',
          route: '/'
        },
          {
            label: 'Snapshots',
            route: ['/', 'fe', 'dashboard']
          },
        {
          label: `Snapshot: ${snapshot.name}`
        }
      ]

    }
}
