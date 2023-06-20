import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {faPlay, faTimes} from '@fortawesome/free-solid-svg-icons';
import {HasConfirmationDialogMixin, ModalService} from "@fabric/common";
import {DataSourceSelection} from "@fabric/component";
import {ConsoleDescriptorDto, NavItem, PagingDto, QueryDto, SnippetDto} from "@fabric/models";
import {ConsoleServiceOld} from "../console.service-old";
import {HistoryItem} from "./history-panel/history-panel.component";
import {SnippetDialogData, SnippetDialogSpec} from "./snippet-dialog/snippet-dialog.component";
import {QueryPanelState} from "./query-panel/query-panel.component";

type TabState = 'HISTORY' | 'SNIPPETS'

export interface QueryPanelSpec {
    state: QueryPanelState
    selectPage: (page: PagingDto) => void
}

@Component({
    selector: 'app-console-panel',
    templateUrl: './console-panel.component.html',
    styleUrls: ['./console-panel.component.scss'],
    providers: [ConsoleServiceOld]
})
export class ConsolePanelComponent extends HasConfirmationDialogMixin implements OnChanges {

    faPlay = faPlay
    faTimes = faTimes

    constructor(protected modalService: ModalService, private service: ConsoleServiceOld) {
        super(modalService)
    }

    @Input()
    dataSource: DataSourceSelection

    ngOnChanges(changes: SimpleChanges) {
        let systemType = this.dataSource.systemComponent.systemType;
        //this.service.loadNavItems(systemComponentKey).then(navItems => this.initNavItems(navItems))
        this.service.loadDescriptor(systemType).then(descriptor => this.initForm(descriptor))
        this.service.loadSnippets(systemType).then(snippets => this.initSnippets(snippets))
    }

    descriptor: ConsoleDescriptorDto

    // input query

    formGroup: FormGroup = new FormGroup({
        fields: new FormArray([])
    })

    get fieldsControl(): FormArray {
        return <FormArray>this.formGroup.get('fields')
    }

    fieldControl(i: number): FormControl {
        return <FormControl>this.fieldsControl.controls[i]
    }

    private initForm(descriptorDto: ConsoleDescriptorDto) {
        this.descriptor = descriptorDto
        this.fieldsControl.controls = []
        this.descriptor.items.forEach(item =>
            this.fieldsControl.push(new FormControl(null))
        )
    }

    doQuery() {
        let attributes = {}
        this.fieldsControl.value.forEach((fieldValue, i) => {
            attributes[this.descriptor.items[i].name] = fieldValue
        })
        let paging: PagingDto = {
            pageNr: 1,
            pageSize: 5
        }
        let query: QueryDto = <QueryDto>{
            environmentKey: this.dataSource.environment.key,
            systemComponentKey: this.dataSource.systemComponent.key,
            attributes,
            navItem: this.currentNavItem,
            paging
        }

        this.service.runQuery(query)
    }

    clear() {
        this.descriptor.items.forEach((descriptorItem, index) => {
                this.fieldControl(index).setValue(null)
            }
        )
        this.service.clearOutput()
    }

    // output panel

    // output
    output$ = this.service.output$

    getKeys(map): string[]{
        if (map) return Object.keys(map)
        return []
    }

    getQueryPanelSpec(queryPanelState: QueryPanelState, queryPanelId: string): QueryPanelSpec {
        return {
            state: queryPanelState,
            selectPage: (page: PagingDto) => this.service.selectPage(queryPanelId, page)
        }
    }


    // tabs history/snippet

    tabState: TabState = 'SNIPPETS'

    selectTab(tabState: TabState) {
        this.tabState = tabState
    }

    // navitems

    navItems: NavItem[]
    currentNavItem: NavItem

    private initNavItems(navItems: NavItem[]) {
        return this.navItems = navItems;
    }

    navItemSelected(navItem: NavItem) {
        this.currentNavItem = navItem
        this.descriptor.items.forEach((descriptorItem, index) => {
                let value = navItem.attributes[descriptorItem.name]
                this.fieldControl(index).setValue(value)
            }
        )
        this.doQuery()
    }


    // history

    historyItems: HistoryItem[] = this.service.historyItems

    deleteHistoryItem(item: HistoryItem) {
        const index = this.historyItems.indexOf(item, 0);
        if (index > -1) {
            this.historyItems.splice(index, 1);
        }
    }

    historyCount(): string {
        return this.historyItems.length == 0 ? '' : `(${this.historyItems.length})`
    }

    convertHistoryItemToSnippet(historyItem: HistoryItem) {
        this.openSnippetDialog({
            mode: 'NEW',
            descriptor: this.descriptor,
            data: {
                attributes: historyItem.attributes
            },
            cancel: () => this.closeSnippetDialog(),
            confirm: (data) => {
                this.addSnippet(this.descriptor, data);
                this.closeSnippetDialog()
            }
        })
    }

    selectHistoryItem(historyItem: HistoryItem) {
        this.descriptor.items.forEach((descriptorItem, index) => {
                let value = historyItem.attributes[descriptorItem.name]
                this.fieldControl(index).setValue(value)
            }
        )
    }


    // snippets

    snippets: SnippetDto[]

    private initSnippets(snippets: SnippetDto[]) {
        return this.snippets = snippets;
    }

    snippetSelected(snippet: SnippetDto) {
        this.descriptor.items.forEach((item, index) => {
            let value = snippet.attributes[item.name]
            this.fieldControl(index).setValue(value)
        })
    }

    snippetEdit(snippet: SnippetDto) {
        this.openSnippetDialog({
            mode: 'EDIT',
            descriptor: this.descriptor,
            data: {
                id: snippet.id,
                title: snippet.title,
                attributes: snippet.attributes
            },
            cancel: () => this.closeSnippetDialog(),
            confirm: (data) => {
                this.updateSnippet(this.descriptor, data);
                this.closeSnippetDialog()
            }
        })

    }

    snippetDelete(snippet: SnippetDto) {
        this.openConfirmationDialog({
            title: 'Confirm Delete Snippet',
            message:  `Delete snippet '${snippet.title}'?`,
            cancel: () => this.closeConfirmationDialog(),
            confirm: () => { this.deleteSnippet(snippet); this.closeConfirmationDialog() }
        })
    }


    private addSnippet(descriptor: ConsoleDescriptorDto, data: SnippetDialogData) {
        let attributes = this.attributeMap(descriptor, data);
        this.service.addSnippet(data.title, this.dataSource.systemComponent.systemType, attributes)
            .then((snippets) => {
                this.snippets = snippets
            })
    }

    private attributeMap(descriptor: ConsoleDescriptorDto, data: SnippetDialogData) {
        let attributes = {}
        descriptor.items.forEach((item, index) => {
            attributes[item.name] = data.fields[index]
        })
        return attributes;
    }

    private updateSnippet(descriptor: ConsoleDescriptorDto, data: SnippetDialogData) {
        let attributes = this.attributeMap(descriptor, data);
        this.service.editSnippet(data.id, data.title, this.dataSource.systemComponent.systemType, attributes)
            .then((snippets) => {
                this.snippets = snippets
            })

    }

    private deleteSnippet(data: SnippetDialogData) {
        this.service.deleteSnippet(data.id, this.dataSource.systemComponent.systemType)
            .then((snippets) => {
                this.snippets = snippets
            })

    }


    // snippet dialog

    snippetDialogId(): string {
        return 'snippet-dialog-id'
    }

    snippetDialogSpec: SnippetDialogSpec;

    private openSnippetDialog(spec: SnippetDialogSpec) {
        this.snippetDialogSpec = spec
        this.modalService.open(this.snippetDialogId())
    }

    private closeSnippetDialog() {
        this.snippetDialogSpec = null
        this.modalService.close(this.snippetDialogId())
    }

    // snippet deletion confirmation

    confirmationDialogId() {
        return "confirm-snippet-delete"
    }

    confirmationDialogSpec: any;

}
