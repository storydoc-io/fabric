import { Component, OnInit } from '@angular/core';
import { DashboardService } from "./dashboard.service";
import {ModalService} from "../common/modal/modal-service";
import {SnapshotDialogData, SnapshotDialogSpec} from "./snapshot-dialog/snapshot-dialog.component";
import {SystemDescriptionService} from "../system-description-page/system-description.service";

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss']
})
export class DashboardPageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }



}
