import {Component, Input, OnInit} from '@angular/core';
import {faLightbulb} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-intro',
  templateUrl: './intro.component.html',
  styleUrls: ['./intro.component.scss']
})
export class IntroComponent implements OnInit {

  faLightbulb = faLightbulb

  constructor() { }

  @Input()
  title: string

  ngOnInit(): void {
  }

}
