import {Injectable} from '@angular/core';
import {ConnectionStatus} from "../styleguide/status/status.component";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ConnectionStatusService {

  status$ = new BehaviorSubject<ConnectionStatus>({ status: 'OK', msg: null} )

  constructor() { }

  setStatus(status: ConnectionStatus) {
    this.status$.next(status)
  }
}
