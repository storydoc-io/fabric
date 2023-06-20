import {Component, EventEmitter, Input, Output} from '@angular/core';
import {DocumentsResultSet, PagingDto, Row} from "@fabric/models";

@Component({
    selector: 'app-documents',
    templateUrl: './documents.component.html',
    styleUrls: ['./documents.component.scss']
})
export class DocumentsComponent {

    constructor() {
    }

    ngOnInit(): void {
    }

    @Output()
    onRowSelect: EventEmitter<Row> = new EventEmitter<Row>();

    @Output()
    onPageSelect: EventEmitter<PagingDto> = new EventEmitter<PagingDto>();

    @Input()
    documents: DocumentsResultSet

    selected: Row

    selectRow(row: Row) {
        this.selected = row
        this.onRowSelect.emit(row)
    }

    selectPage(pagingInfo: PagingDto) {
        this.onPageSelect.emit(pagingInfo)
    }

    prettyPrint(jsonString: string): string {
        return JSON.stringify(JSON.parse(jsonString), null, 2)
    }

}
