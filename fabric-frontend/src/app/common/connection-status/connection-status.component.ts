import {Component, OnInit} from '@angular/core';
import {ConnectionStatusService} from "./connection-status.service";

@Component({
  selector: 'app-connection-status',
  templateUrl: './connection-status.component.html',
  styleUrls: ['./connection-status.component.scss']
})
export class ConnectionStatusComponent implements OnInit {

  constructor(private service: ConnectionStatusService) { }

  ngOnInit(): void {
  }

  get status(): string {
    return this.service.status
  }

}
