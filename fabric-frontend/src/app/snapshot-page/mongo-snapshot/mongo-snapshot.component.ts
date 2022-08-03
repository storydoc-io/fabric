import {Component, Input, OnInit} from '@angular/core';
import {CollectionNavItem, CollectionSnapshot, MongoSnapshot, SnapshotId} from "@fabric/models";
import {MongoService} from "./mongo.service";
import {MongoNavigationModelService} from "./mongo-navigation-model.service";

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

  selection: CollectionNavItem

  mongoSnapshot$ = this.service.mongoSnapshot$

  mongoNavigationModels$ = this.navigationService.navigationModels$;

  constructor(private service: MongoService, private navigationService: MongoNavigationModelService) { }

  ngOnInit(): void {
      this.navigationService.load(this.componentKey)
      this.service.load(this.snapshotId, this.componentKey);
  }

  select(selected: CollectionNavItem) {
    this.selection = selected
  }

  selectedCollection(mongoSnapshot: MongoSnapshot, selection: CollectionNavItem): CollectionSnapshot {
    if (!selection) return null
    return mongoSnapshot.collectionSnapshots.find(collectionSnapshot => collectionSnapshot.collectionName===selection.collectionName)
  }
}
