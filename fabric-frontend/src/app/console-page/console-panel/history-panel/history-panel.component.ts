import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ActionsSpec} from "@fabric/common";

export interface HistoryItem {
  attributes?: { [key: string]: string };
}

@Component({
  selector: 'app-history-panel',
  templateUrl: './history-panel.component.html',
  styleUrls: ['./history-panel.component.scss']
})
export class HistoryPanelComponent {

  @Input()
  historyItems: HistoryItem[]

  @Output()
  onSelect = new EventEmitter<HistoryItem>()

  @Output()
  onConvert = new EventEmitter<HistoryItem>()

  @Output()
  onDelete = new EventEmitter<HistoryItem>()

  select(item: HistoryItem) {
    this.onSelect.emit(item)
  }

  asOneLine(item: HistoryItem): string {
    let line = ''
    Object.keys(item.attributes).forEach((key, index) => {
      let value = item.attributes[key]
      if (value) {
        line += index > 0 ? ' ' : ''
        line += item.attributes[key]
      }
    })
    return line
  }

  convert(item: HistoryItem) {
    this.onConvert.emit(item)
  }

  delete(item: HistoryItem) {
    this.onDelete.emit(item)
  }

  actions(item: HistoryItem): ActionsSpec {
    return {
      actions: [
        {
            label: 'To Snippet',
            handler: () => this.convert(item)
        },
        {
          label: 'Select',
          handler: () => this.select(item)
        },
        {
          label: 'Delete',
          handler: () => this.delete(item)
        },
      ]
    }
  }
}
