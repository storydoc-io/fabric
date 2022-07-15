import { Component, OnInit } from '@angular/core';
import {SystemDescriptionService} from "./system-description.service";

@Component({
  selector: 'app-system-description-page',
  templateUrl: './system-description-page.component.html',
  styleUrls: ['./system-description-page.component.scss']
})
export class SystemDescriptionPageComponent implements OnInit {

  constructor(private service: SystemDescriptionService) { }

  systemDescription$ = this.service.systemDescription$

  ngOnInit(): void {
  }

}
