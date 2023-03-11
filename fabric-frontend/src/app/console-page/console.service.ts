import {Injectable} from '@angular/core';
import {ConsoleControllerService} from "@fabric/services";
import {ConsoleDescriptorDto, NavItem, QueryCompositeDto, SnippetDto, TabularResultSet} from "@fabric/models";
import {BehaviorSubject} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {logChangesToObservable} from "@fabric/common";

export interface Output {
    jsonOutput: string
    stackTraceOutput: string
    tabularResponse: TabularResultSet
}

interface ConsoleState {
    rootQueryComposite: QueryCompositeDto
    selection: QueryCompositeDto
    output: Output

}



@Injectable()
export class ConsoleService {

    constructor(private consoleControllerService: ConsoleControllerService) {
        this.init()
    }

    subscriptions = []

    init() {
        this.subscriptions.push(logChangesToObservable('store::output$ >>', this.output$))
    }

    private store = new BehaviorSubject<ConsoleState>(this.initialConsoleState())

    root$ = this.store.pipe(
        map(state => state.rootQueryComposite),
        distinctUntilChanged(),
    )

    selection$ = this.store.pipe(
        map(state => state.selection),
        distinctUntilChanged(),
    )

    output$ = this.store.pipe(
        map(state => state.output),
        distinctUntilChanged(),
    )

    private initialConsoleState(): ConsoleState  {
         let rootQueryComposite = this.createQueryComposite()
         return {
             rootQueryComposite,
             selection: rootQueryComposite,
             output: {
                 jsonOutput: null,
                 stackTraceOutput: null,
                 tabularResponse: null
             }
         }
    }

    private idCounter: 0

    private createQueryComposite(): QueryCompositeDto {
        return {
            id: "" + this.idCounter++,
            children: []
        }
    }

    addQuery() {
    }

    clearOutput() {
        this.store.next({
            ... this.store.value,
            output: {
                jsonOutput: null,
                stackTraceOutput: null,
                tabularResponse: null
            }
        })
    }


    loadDescriptor(systemType: string) : Promise<ConsoleDescriptorDto>{
        return this.consoleControllerService.getDescriptorUsingGet({systemType})
            .toPromise()
    }

    runRequest(queryComposite: QueryCompositeDto) {
        this.clearOutput();
        return this.consoleControllerService.runRequestUsingPost({
            body: queryComposite
        }).subscribe((dto)=> {

            this.store.value.selection.result = dto.result

            let result = dto.result
            let output
            switch (result.resultType) {
                case 'JSON': {
                    output = {
                        jsonOutput : JSON.parse(result.content)
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
            this.store.next({
                ... this.store.value,
                output
            })

            if (result.resultType != 'STACKTRACE') {
                // this.addHistoryItem(attributes)
            }


        })
    }

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
