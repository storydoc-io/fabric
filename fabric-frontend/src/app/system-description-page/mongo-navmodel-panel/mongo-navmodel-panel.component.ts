import { Component, OnInit } from '@angular/core';
import {MongoNavigationModelService} from "../../snapshot-page/mongo-snapshot/mongo-navigation-model.service";

@Component({
  selector: 'app-mongo-navmodel-panel',
  templateUrl: './mongo-navmodel-panel.component.html',
  styleUrls: ['./mongo-navmodel-panel.component.scss']
})
export class MongoNavmodelPanelComponent implements OnInit {

  constructor(private navigationService: MongoNavigationModelService) { }

  mongoNavigationModels$ = this.navigationService.navigationModels$;

  ngOnInit(): void {
    this.navigationService.load('MONGODB')
  }

}
