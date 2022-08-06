import {Component, Input, OnInit} from '@angular/core';
import {CollectionNavItem, CollectionSnapshot, MongoSnapshot, SnapshotId} from "@fabric/models";
import {MongoService} from "./mongo.service";
import {MongoNavigationModelService} from "./mongo-navigation-model.service";

interface Selector {
 type: string
}

interface RootSelector extends Selector {
  type: 'ROOT'
}

interface CollectionSelector extends Selector {
  type: 'COLLECTION'
}

interface DocumentSelector extends Selector {
  type: 'COLLECTION'
}


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

  collectionSelection: CollectionNavItem

  documentSelection: number | null = null

  mongoSnapshot$ = this.service.mongoSnapshot$

  mongoNavigationModels$ = this.navigationService.navigationModels$;

  constructor(private service: MongoService, private navigationService: MongoNavigationModelService) { }

  ngOnInit(): void {
      this.navigationService.load(this.componentKey)
      this.service.load(this.snapshotId, this.componentKey);
  }

  selectCollection(selectedCollection: CollectionNavItem) {
    this.collectionSelection = selectedCollection
    this.documentSelection = null
  }

  selectedCollection(mongoSnapshot: MongoSnapshot, selectedCollection: CollectionNavItem): CollectionSnapshot {
    if (!selectedCollection) return null
    return mongoSnapshot.collectionSnapshots.find(collectionSnapshot => collectionSnapshot.collectionName===selectedCollection.collectionName)
  }


  selectDocument(docIdx: number) {
      this.documentSelection = docIdx
  }

  selectedDocument(collection: CollectionSnapshot, documentSelection: number): object {
     if (collection==null || documentSelection==null) return null
     let result: string = collection.documents[documentSelection]
    return JSON.parse(result)
  }

}
