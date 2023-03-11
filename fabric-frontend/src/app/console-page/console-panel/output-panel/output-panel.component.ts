import {Component, Input, OnInit} from '@angular/core';
import {Output} from "../../console.service";
import {Row} from "@fabric/models";

@Component({
  selector: 'app-output-panel',
  templateUrl: './output-panel.component.html',
  styleUrls: ['./output-panel.component.scss']
})
export class OutputPanelComponent implements OnInit {

  constructor() { }

  @Input()
  output: Output

  selectTableRow(row: Row) {
    console.log('selected: ', row)
  }


  ngOnInit(): void {
  }

}
