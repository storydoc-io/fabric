import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Row, TabularResponse} from "@fabric/models";

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

    @Input()
    tabular: TabularResponse

    selected: Row

    selectRow(row: Row) {
        this.selected = row
        this.onRowSelect.emit(row)
    }
}
