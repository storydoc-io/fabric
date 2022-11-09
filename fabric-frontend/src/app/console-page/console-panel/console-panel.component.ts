import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {faPlay, faTimes} from '@fortawesome/free-solid-svg-icons';
import {ModalService} from "@fabric/common";
import {DataSourceSelection} from "@fabric/component";
import {ConsoleDescriptorDto, NavItem, Row, SnippetDto, TabularResponse} from "@fabric/models";
import {ConsoleService} from "../console.service";
import {HistoryItem} from "./history-panel/history-panel.component";
import {SnippetDialogData, SnippetDialogSpec} from "./snippet-dialog/snippet-dialog.component";

type TabState = 'HISTORY' | 'SNIPPETS'

@Component({
    selector: 'app-console-panel',
    templateUrl: './console-panel.component.html',
    styleUrls: ['./console-panel.component.scss'],
    providers: [ConsoleService]
})
export class ConsolePanelComponent implements OnChanges {

    faPlay = faPlay
    faTimes = faTimes

    constructor(private modalService: ModalService, private service: ConsoleService) {
    }

    @Input()
    dataSource: DataSourceSelection

    ngOnChanges(changes: SimpleChanges) {
        let systemComponentKey = this.dataSource.systemComponentKey;
        //this.service.loadNavItems(systemComponentKey).then(navItems => this.initNavItems(navItems))
        this.service.loadDescriptor(systemComponentKey).then(descriptor => this.initForm(descriptor))
        this.service.loadSnippets(systemComponentKey).then(snippets => this.initSnippets(snippets))
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
        this.service.runRequest(
            this.dataSource.environmentKey,
            this.dataSource.systemComponentKey,
            attributes,
            this.currentNavItem
        ).then((response) => {
            this.clearOutput();
            switch (response.consoleOutputType) {
                case 'JSON': {
                    this.jsonOutput = JSON.parse(response.content)
                    break
                }
                case 'STACKTRACE': {
                    this.stackTraceOutput = response.content
                    break
                }
                case 'TABULAR' : {
                    this.tabularResponse = response.tabular
                }
            }
            if (response.consoleOutputType != 'STACKTRACE') {
                this.addHistoryItem(attributes)
            }
        })
    }

    private clearOutput() {
        this.jsonOutput = null
        this.stackTraceOutput = null
        this.tabularResponse = null
    }

    clear() {
        this.descriptor.items.forEach((descriptorItem, index) => {
                this.fieldControl(index).setValue(null)
            }
        )
        this.clearOutput()
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

    historyItems: HistoryItem[] = []

    private addHistoryItem(attributes: {}) {
        this.historyItems.push({
            attributes
        })
    }

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

    private addSnippet(descriptor: ConsoleDescriptorDto, data: SnippetDialogData) {
        let attributes = {}
        descriptor.items.forEach((item, index) => {
            attributes[item.name] = data.fields[index]
        })
        this.service.addSnippet(data.title, this.dataSource.systemComponentKey, attributes)
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


    // output

    jsonOutput: string
    stackTraceOutput: string
    tabularResponse: TabularResponse

    selectTableRow(row: Row) {
        console.log('selected: ', row)
    }


}
