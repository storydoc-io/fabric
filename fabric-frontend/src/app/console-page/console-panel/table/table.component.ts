import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PagingDto, Row, TabularResultSet} from "@fabric/models";

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

    constructor() {
    }

    ngOnInit(): void {
    }

    @Output()
    onRowSelect: EventEmitter<Row> = new EventEmitter<Row>();

    @Output()
    onPageSelect: EventEmitter<PagingDto> = new EventEmitter<PagingDto>();

    @Input()
    tabular: TabularResultSet

    selected: Row

    selectRow(row: Row) {
        this.selected = row
        this.onRowSelect.emit(row)
    }

    selectPage(pagingInfo: PagingDto) {
        this.onPageSelect.emit(pagingInfo)
    }

}
