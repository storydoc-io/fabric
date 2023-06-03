import {Injectable} from '@angular/core';
import {ConsoleControllerService} from "@fabric/services";
import {ConsoleDescriptorDto, NavItem, PagingDto, QueryDto, ResultDto, SnippetDto} from "@fabric/models";
import {BehaviorSubject} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {logChangesToObservable} from "@fabric/common";
import {QueryOutput, QueryPanelState} from "./console-panel/query-panel/query-panel.component";


interface ConsoleState {
    selection: string
    queryPanelStates: { [id: string]: QueryPanelState }
}



@Injectable()
export class ConsoleServiceOld {

    constructor(private consoleControllerService: ConsoleControllerService) {
        this.init()
    }

    subscriptions = []

    init() {
        this.subscriptions.push(logChangesToObservable('store::output$ >>', this.output$))
    }

    private idCounter: number = 0

    private store = new BehaviorSubject<ConsoleState>(this.initialConsoleState())

    output$ = this.store.pipe(
        map(state => state.queryPanelStates),
        distinctUntilChanged(),
    )

    private initialConsoleState(): ConsoleState  {
         let initialPanel = this.addPanel()
         return {
             selection: initialPanel.id,
             queryPanelStates: {
                 [initialPanel.id] : initialPanel
             }
         }
    }

    private getSelectedPanel(): string {
        return this.store.value.selection
    }

    private addPanel(): QueryPanelState {
        return <QueryPanelState>{
            id: "panel_" + (this.idCounter++)
        }
    }

    addQuery() {
    }

    clearOutput() {
        let queryId = this.getSelectedPanel()
        let state = this.store.value
        this.store.next({
            ... state,
            queryPanelStates: {
                ... state.queryPanelStates,
                [queryId]: <QueryPanelState>{}
            }
        })
    }


    loadDescriptor(systemType: string) : Promise<ConsoleDescriptorDto>{
        return this.consoleControllerService.getDescriptorUsingGet({systemType})
            .toPromise()
    }

    runQuery(query: QueryDto) {
        let queryId = this.getSelectedPanel()
        this.clearOutput();
        return this.consoleControllerService.runRequestUsingPost({
            body: query
        }).subscribe((result)=> {
            this.setResult(queryId, query, result)
        })
    }

    selectPage(queryId: string, paging: PagingDto) {
        let queryPanelState = this.store.value.queryPanelStates[queryId]
        let query =  {
            ... queryPanelState.query,
            paging
        }
        this.consoleControllerService.runRequestUsingPost({
            body: query
        }).subscribe((result => {
            console.log('result: ', result)
            this.setResult(queryId, query, result)
        }))

    }



    private setResult(queryId: string, request: QueryDto, result: ResultDto) {
        this.store.value.selection = queryId

        let output: QueryOutput
        switch (result.resultType) {
            case 'JSON': {
                if  (result.documentsResultSet) {
                    output = {
                        documentsResponse : result.documentsResultSet
                    }
                } else {
                    output = {
                        jsonOutput : JSON.parse(result.content)
                    }
                }
                break
            }
            case 'STACKTRACE': {
                output = {
                    stackTraceOutput : result.content
                }
                break
            }
            case 'TABULAR' : {
                output = {
                    tabularResponse : result.tabular
                }
            }
        }
        let state = this.store.value
        this.store.next({
            ... state,
            queryPanelStates: {
                ... state.queryPanelStates,
                [queryId]: <QueryPanelState>{
                    query: request,
                    output: output
                }
            }
        })

        if (result.resultType != 'STACKTRACE') {
            // this.addHistoryItem(attributes)
        }


    }


    // snippets

    loadSnippets(systemType:string): Promise<SnippetDto[]> {
        return this.consoleControllerService.getSnippetsUsingGet({systemType}).toPromise()
    }

    addSnippet(title: string, systemType: string, attributes: {}): Promise<SnippetDto[]> {
        return this.consoleControllerService.createSnippetUsingPost({
            systemType,
            body: {
                title,
                attributes
            }
        }).toPromise().then(()=> this.loadSnippets(systemType))

    }

    editSnippet(id: string, title: string, systemType: string, attributes: {}) {
        return this.consoleControllerService.updateSnippetUsingPut({
            systemType,
            body: {
                id,
                title,
                attributes
            }
        }).toPromise().then(()=> this.loadSnippets(systemType))
    }

    deleteSnippet(id: string, systemType: string) {
        return this.consoleControllerService.deleteSnippetUsingDelete({
            systemType,
            id
        }).toPromise().then(()=> this.loadSnippets(systemType))
    }

    loadNavItems(systemComponentKey: string): Promise<NavItem[]>  {
        return this.consoleControllerService.getNavigationUsingPost({body: {
            systemComponentKey,
        }}).toPromise()
    }


}
