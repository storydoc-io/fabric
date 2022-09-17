import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ConsoleService} from "../console.service";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {DataSourceSelection} from "../data-source-selection-panel/data-source-selection-panel.component";
import {faSearch} from '@fortawesome/free-solid-svg-icons';
import {ConsoleDescriptorDto, SnippetDto} from "@fabric/models";

@Component({
  selector: 'app-console-panel',
  templateUrl: './console-panel.component.html',
  styleUrls: ['./console-panel.component.scss'],
  providers: [ConsoleService]
})
export class ConsolePanelComponent implements OnChanges {

  faSearch = faSearch

  constructor(private service: ConsoleService) { }

  @Input()
  dataSource: DataSourceSelection

  ngOnChanges(changes: SimpleChanges) {
    let systemComponentKey = this.dataSource.systemComponentKey;
    this.service.loadDescriptor(systemComponentKey).then((descriptor)=> this.initForm(descriptor))
    this.service.loadSnippets(systemComponentKey).then((snippets)=>this.initSnippets(snippets))
  }

  descriptor: ConsoleDescriptorDto

  formGroup: FormGroup = new FormGroup({
    fields: new FormArray([])
  })

  get fieldsControl():FormArray {
    return <FormArray> this.formGroup.get('fields')
  }

  fieldControl(i: number): FormControl {
    return <FormControl> this.fieldsControl.controls[i]
  }

  private initForm(descriptorDto: ConsoleDescriptorDto) {
    this.descriptor = descriptorDto
    this.fieldsControl.controls = []
    this.descriptor.items.forEach(item =>
        this.fieldsControl.push(new FormControl(null))
    )
  }

  snippets: SnippetDto[]

  private initSnippets(snippets: SnippetDto[]) {
    return this.snippets = snippets;
  }

  jsonOutput: string
  stackTraceOutput: string

  doQuery() {
    let attributes = {}
    console.log('attributes:', attributes)
    console.log('value', this.fieldsControl.value)
    this.fieldsControl.value.forEach((fieldValue, i) => {
      attributes[this.descriptor.items[i].name] = fieldValue
    })
    console.log('attributes:', attributes)
    this.service.runRequest(
        this.dataSource.environmentKey,
        this.dataSource.systemComponentKey,
        attributes
    ).then((response) => {
      this.jsonOutput = null
      this.stackTraceOutput = null
      switch (response.consoleOutputType) {
        case 'JSON': {
          this.jsonOutput = JSON.parse(response.content)
          break
        }
        case 'STACKTRACE': {
          this.stackTraceOutput = response.content
          break
        }
      }
    })
  }

  snippetSelected(snippet: SnippetDto) {
    this.descriptor.items.forEach((item, index) => {
      let value = snippet.attributes[item.name]
      console.log('item: ', item.name)
      console.log('value: ', value)
      this.fieldControl(index).setValue(value)
    })

  }
}
