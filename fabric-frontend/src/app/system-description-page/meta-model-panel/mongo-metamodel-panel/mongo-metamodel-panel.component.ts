import { Component, OnInit } from '@angular/core';
import {MongoMetaModelService} from "./mongo-metamodel.service";

@Component({
  selector: 'app-mongo-metamodel-panel',
  templateUrl: './mongo-metamodel-panel.component.html',
  styleUrls: ['./mongo-metamodel-panel.component.scss']
})
export class MongoMetamodelPanelComponent implements OnInit {

  constructor(private mongoMetaModelService: MongoMetaModelService) { }

  metaModels$ = this.mongoMetaModelService.metaModels$

  ngOnInit(): void {
    this.mongoMetaModelService.load('MONGODB')
  }

}
