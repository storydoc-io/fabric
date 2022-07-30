import {Component, Input, OnInit} from '@angular/core';
import {SnapshotId} from "@fabric/models";
import {MongoService} from "./mongo.service";

@Component({
  selector: 'app-mongo-snapshot',
  templateUrl: './mongo-snapshot.component.html',
  styleUrls: ['./mongo-snapshot.component.scss']
})
export class MongoSnapshotComponent implements OnInit {

  @Input()
  snapshotId: SnapshotId

  @Input()
  componentKey: string

  mongoSnapshot$ = this.service.mongoSnapshot$

  constructor(private service: MongoService) { }

  ngOnInit(): void {
      this.service.load(this.snapshotId, this.componentKey);
  }

}
