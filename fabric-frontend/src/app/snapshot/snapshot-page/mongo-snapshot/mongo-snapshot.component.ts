import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {CollectionNavItem, CollectionSnapshot, MongoSnapshot, SnapshotId} from "@fabric/models";
import {MongoService} from "./mongo.service";
import {MongoNavigationModelService} from "./mongo-navigation-model.service";
import {Subscription} from "rxjs";

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

const DEFAULT_PAGE_SIZE = 20;

class PagedCollection {


  constructor(private collection: CollectionSnapshot){
    this.pageSize = DEFAULT_PAGE_SIZE
    this.lastPageNr = this.collection.documents.length==0 ? 0 : Math.floor((this.collection.documents.length-1) / this.pageSize)
    this.toFirstPage()
  }

  pageSize: number
  pageNr: number
  lastPageNr: number
  currentPage: string[]

  toFirstPage() {
    this.pageNr =  0
    this.setCurrentPage()
  }

  toPreviousPage() {
    this.pageNr =  this.pageNr == 0 ? 0 : this.pageNr-1
    this.setCurrentPage()
  }

  toNextPage() {
    this.pageNr =  this.pageNr == this.lastPageNr ? this.lastPageNr : this.pageNr+1
    this.setCurrentPage()
  }

  toLastPage() {
    this.pageNr =  this.lastPageNr
    this.setCurrentPage()
  }

  needsPager() {
    return this.lastPageNr > 0
  }

  setCurrentPage() {
    let start  = this.pageNr*this.pageSize
    let end = Math.min(start+this.pageSize, this.collection.documents.length-1)
    this.currentPage = this.collection.documents.slice(start,end)

  }

  getIdx(idx: number): number {
    return this.pageNr*this.pageSize + idx
  }

}


@Component({
  selector: 'app-mongo-snapshot',
  templateUrl: './mongo-snapshot.component.html',
  styleUrls: ['./mongo-snapshot.component.scss']
})
export class MongoSnapshotComponent implements OnInit, OnDestroy {

  @Input()
  snapshotId: SnapshotId

  @Input()
  componentKey: string

  mongoNavigationModels$ = this.navigationService.navigationModels$;

  mongoSnapshot$ = this.service.mongoSnapshot$
  mongoSnapshot: MongoSnapshot

  

  selectedCollection: CollectionSnapshot
  pagedSelectedCollection: PagedCollection

  documentSelection: number | null = null

  constructor(private service: MongoService, private navigationService: MongoNavigationModelService) { }

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
      this.subscriptions.push(this.mongoSnapshot$.subscribe((mongoSnapshot)=> {
        this.mongoSnapshot = mongoSnapshot
      }))
      this.navigationService.load(this.componentKey)
      this.service.load(this.snapshotId, this.componentKey);

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  selectCollection(collectionNavItem: CollectionNavItem) {
    this.selectedCollection = this.mongoSnapshot.collectionSnapshots.find((collection)=>collection.collectionName===collectionNavItem.collectionName)
    this.pagedSelectedCollection = new PagedCollection(this.selectedCollection)
    this.documentSelection = null
  }

  selectDocument(docIdx: number) {
      this.documentSelection = this.pagedSelectedCollection.getIdx(docIdx)
  }

  selectedDocument(): object {
     if (this.documentSelection==null) return null
     let result: string = this.selectedCollection.documents[this.documentSelection]
    return JSON.parse(result)
  }


}
