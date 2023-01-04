import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {faAngleRight} from "@fortawesome/free-solid-svg-icons";
import {EntityDto} from "@fabric/models";

// data model

export interface EntityItem {
    entity_id: string
    entity_ref: string
    name: string
    attributes: AttributeItem[]
}


export interface AttributeItem {
    name: string
    entries: EntityItem[]
}

// view model

export interface ViewModel {
    columns: ColumnPart[]
}

export interface ColumnPart {
    title: string
    lists: ListPart[]
}

export interface ListPart {
    title: string
    entries: ListEntryPart[]
}

export interface ListEntryPart {
    label: string
    json: boolean
    lists: ListPart[]
    entry: EntityItem
    selected: boolean
}


class ViewModelReducer {

    constructor(private entity: EntityDto) { this.init()}

    viewModel: ViewModel

    private init() {
        this.viewModel = {
            columns: [
                this.toColumn(this.entity)
            ]
        }
    }

    private toColumn(item: EntityDto): ColumnPart {
        return {
            title: item.name,
            lists: item.attributes.map((list)=> {
                return <ListPart>{
                    title: list.name,
                    entries: list.entries.map((entry) => {
                        return {
                            label: entry.name,
                            selected: false,
                            json: entry.json,
                            entry
                        }
                    })
                }
            })
        }

    }

    select(column: ColumnPart, entry: ListEntryPart) {
        let columnIdx = this.columnIdx(column)
        this.viewModel.columns = this.viewModel.columns.slice(0, columnIdx+1)
        this.viewModel.columns.push(this.toColumn(entry.entry))
        column.lists.forEach(list => {
            list.entries.forEach(entry2 => entry2.selected = entry===entry2)
        })
    }

    private columnIdx(colomn: ColumnPart): number {
        return this.viewModel.columns.indexOf(colomn)
    }

    deselect(column: ColumnPart, entry: ListEntryPart) {
        let columnIdx = this.columnIdx(column)
        this.viewModel.columns = this.viewModel.columns.slice(0, columnIdx+1)
        column.lists.forEach(list => {
            list.entries.forEach(entry2 => entry2.selected = false)
        })
    }
}


@Component({
    selector: 'app-meta-model-view',
    templateUrl: './meta-model-view.component.html',
    styleUrls: ['./meta-model-view.component.scss']
})
export class MetaModelViewComponent implements OnInit, OnChanges {

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.entity) {
            console.log('entity changed', this.entity)
            this.viewModelReducer = new ViewModelReducer(this.entity)
            this.viewModel = this.viewModelReducer.viewModel
        }
    }

    viewModelReducer: ViewModelReducer

    @Input()
    entity: EntityDto

    viewModel: ViewModel;
    faAngleRight = faAngleRight


    select(column: ColumnPart, entry: ListEntryPart) {
        if (entry.selected) {
            this.viewModelReducer.deselect(column, entry)
        } else {
            this.viewModelReducer.select(column, entry)
        }
    }


}
