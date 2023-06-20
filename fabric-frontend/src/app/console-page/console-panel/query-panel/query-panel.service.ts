import {Injectable} from "@angular/core";
import {ConsoleControllerService} from "@fabric/services";
import {logChangesToObservable} from "@fabric/common";
import {QueryCompositeDto, TabularResultSet} from "@fabric/models";
import {distinctUntilChanged, map} from "rxjs/operators";
import {BehaviorSubject} from "rxjs";

export interface Output {
    jsonOutput: string
    stackTraceOutput: string
    tabularResponse: TabularResultSet
}

interface OutputPanelState {
    query: QueryCompositeDto
    output: Output

}

@Injectable()
export class QueryPanelService {

    constructor(private consoleControllerService: ConsoleControllerService) {
        this.init()
    }

    subscriptions = []

    private store = new BehaviorSubject<OutputPanelState>(this.initialConsoleState())

    output$ = this.store.pipe(
        map(state => state.output),
        distinctUntilChanged(),
    )

    private initialConsoleState(): OutputPanelState  {
        return {
            query: null,
            output: null
        }
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


    init() {
        this.subscriptions.push(logChangesToObservable('store::result$ >>', this.output$))
    }

    runRequest(queryComposite: QueryCompositeDto) {
        this.clearOutput();
        return this.consoleControllerService.runRequestUsingPost({
            body: queryComposite
        }).subscribe((dto)=> {


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



}