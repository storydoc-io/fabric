import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SnippetDto} from "@fabric/models";

@Component({
  selector: 'app-snippet-panel',
  templateUrl: './snippet-panel.component.html',
  styleUrls: ['./snippet-panel.component.scss']
})
export class SnippetPanelComponent implements OnInit {

  constructor() { }

  @Input()
  snippets: SnippetDto[]

  @Output()
  selected = new EventEmitter<SnippetDto>()

  ngOnInit(): void {
  }

  apply(snippet: SnippetDto) {
    this.selected.emit(snippet)
  }

}
