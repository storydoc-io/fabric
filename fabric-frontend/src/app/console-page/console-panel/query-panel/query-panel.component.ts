import {Component, Input, OnInit} from '@angular/core';
import {PagingDto, QueryDto, Row, TabularResultSet} from "@fabric/models";
import {QueryPanelSpec} from "../console-panel.component";

export interface QueryOutput {
  jsonOutput?: string
  stackTraceOutput?: string
  tabularResponse?: TabularResultSet
}

export interface QueryPanelState {
  id: string
  query?: QueryDto
  output?: QueryOutput
}

@Component({
  selector: 'app-query-panel',
  templateUrl: './query-panel.component.html',
  styleUrls: ['./query-panel.component.scss']
})
export class QueryPanelComponent implements OnInit {

  constructor() { }

  @Input()
  spec: QueryPanelSpec

  selectTableRow(row: Row) {
    console.log('selected: ', row)
  }


  ngOnInit(): void {
  }

  selectPage(pagingDto: PagingDto) {
      this.spec.selectPage(pagingDto)
  }

}
