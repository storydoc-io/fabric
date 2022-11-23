import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SnippetDto} from "@fabric/models";
import {ActionsSpec} from "@fabric/common";

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
  onSelect = new EventEmitter<SnippetDto>()

  @Output()
  onEdit = new EventEmitter<SnippetDto>()

  @Output()
  onDelete = new EventEmitter<SnippetDto>()

  ngOnInit(): void {
  }

  select(snippet: SnippetDto) {
    this.onSelect.emit(snippet)
  }

  edit(snippet: SnippetDto) {
    this.onEdit.emit(snippet)
  }

  delete(snippet: SnippetDto) {
    this.onDelete.emit(snippet)
  }

  actions(snippet: SnippetDto): ActionsSpec {
    return {
      actions: [
        {
          label: 'Edit',
          handler: () => this.edit(snippet)
        },
        {
          label: 'Select',
          handler: () => this.select(snippet)
        },
        {
          label: 'Delete',
          handler: () => this.delete(snippet)
        },
      ]
    }
  }

}
