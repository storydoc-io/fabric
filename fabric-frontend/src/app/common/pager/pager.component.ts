import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import {faFastBackward, faFastForward, faStepBackward, faStepForward} from '@fortawesome/free-solid-svg-icons';
import {PagingDto} from "@fabric/models";

@Component({
  selector: 'app-pager',
  templateUrl: './pager.component.html',
  styleUrls: ['./pager.component.scss']
})
export class PagerComponent implements OnInit {

  faFastBackward=faFastBackward
  faStepBackward=faStepBackward
  faStepForward=faStepForward
  faFastForward=faFastForward

  constructor() { }

  @Input()
  paging: PagingDto

  @Output()
  private onPageSelected = new EventEmitter()

  ngOnInit(): void {
  }

  firstPage(): boolean {
    return this.paging.pageNr == 1
  }

  secondPage(): boolean {
    return this.paging.pageNr == 2
  }

  gapBetweenFirstAndLastPage():boolean {
    return (this.firstPage() || this.lastPage()) && this.pageCount() > 2
  }

  gapBeforeMiddlePage():boolean {
    return this.middlePage() && this.paging.pageNr > 2
  }

  gapAfterMiddlePage():boolean {
    return this.middlePage() && this.paging.pageNr < this.pageCount() - 1
  }

  middlePage(): boolean {
    return !this.firstPage() && !this.lastPage()
  }

  lastPage():boolean {
    return this.paging.pageNr == this.pageCount()
  }

  pageCount(): number {
    return Math.ceil(this.paging.nrOfResults/this.paging.pageSize)
  }

  selectPage(pageNr: number) {
    this.onPageSelected.emit(<PagingDto>{
      pageNr,
      pageSize: this.paging.pageSize
    })

  }
}
