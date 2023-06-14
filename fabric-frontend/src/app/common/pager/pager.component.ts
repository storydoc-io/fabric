import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';

import {faFastBackward, faFastForward, faStepBackward, faStepForward} from '@fortawesome/free-solid-svg-icons';
import {PagingDto} from "@fabric/models";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-pager',
  templateUrl: './pager.component.html',
  styleUrls: ['./pager.component.scss']
})
export class PagerComponent implements OnInit, OnChanges {

  faFastBackward=faFastBackward
  faStepBackward=faStepBackward
  faStepForward=faStepForward
  faFastForward=faFastForward

  constructor() { }

  @Input()
  paging: PagingDto

  @Output()
  private onPageSelected = new EventEmitter()

  @Output()
  private onPageSizeSelected = new EventEmitter()

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.paging) {
      this.pageSizeControl.setValue(this.paging.pageSize)
    }
  }

  private get pageSizeControl(): FormControl {
    return <FormControl>this.formGroup.get('pageSize');
  }

  formGroup: FormGroup = new FormGroup({
    pageSize: new FormControl(null, []),
  })

  sizes = [5, 10, 50, 100, 500];

  from(): number {
    return ((this.paging.pageNr-1)*this.paging.pageSize)+1
  }

  to(): number {
    let lastOfCurrentPage =  (this.paging.pageNr*this.paging.pageSize)
    return Math.min(lastOfCurrentPage, this.paging.nrOfResults)
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

  selectSize() {
    this.onPageSelected.emit(<PagingDto>{
      pageNr: 1,
      pageSize: this.pageSizeControl.value
    })
  }

}
