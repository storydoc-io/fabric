import {Component, Input, OnInit} from '@angular/core';
import {ConsoleService} from "../console.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {DataSourceSelection} from "../data-source-selection-panel/data-source-selection-panel.component";
import {faSearch} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-console-panel',
  templateUrl: './console-panel.component.html',
  styleUrls: ['./console-panel.component.scss']
})
export class ConsolePanelComponent implements OnInit {

  faSearch = faSearch

  constructor(private service: ConsoleService) { }

  @Input()
  dataSource: DataSourceSelection

  ngOnInit(): void {
  }

  formGroup: FormGroup = new FormGroup({
    query1: new FormControl(null, [Validators.required]),
    query2: new FormControl(null, ),
  })

  public get query1Control(): FormControl {
    return <FormControl> this.formGroup.get('query1')
  }

  public get query2Control(): FormControl {
    return <FormControl> this.formGroup.get('query2')
  }

  output: string

  doQuery() {
    this.service.doQuery(
        this.dataSource.environmentKey,
        this.dataSource.systemComponentKey,
        {
            'endpoint' : this.query1Control.value,
            'jsonEntity' : this.query2Control.value
        }
    ).then((response) => {
      this.output = JSON.parse(response.attributes['content'])
    })
  }

}
