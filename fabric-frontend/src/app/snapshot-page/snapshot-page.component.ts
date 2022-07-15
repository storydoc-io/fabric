import { Component, OnInit } from '@angular/core';
import {SnapshotService} from "./snapshot.service";
import {DashboardService} from "../dashboard-page/dashboard.service";

@Component({
  selector: 'app-snapshot-page',
  templateUrl: './snapshot-page.component.html',
  styleUrls: ['./snapshot-page.component.scss'],
  providers: [SnapshotService]
})
export class SnapshotPageComponent implements OnInit {

  constructor(private service: SnapshotService) { }

  snapshot$ = this.service.snapshot$

  ngOnInit(): void {
    this.service.loadSnapshot('');
  }

}
