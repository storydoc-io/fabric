import {Component, Input, OnInit} from '@angular/core';
import {Column, Row} from "@fabric/models";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  rows: Row[]

  @Input()
  columns: Column[]

}
