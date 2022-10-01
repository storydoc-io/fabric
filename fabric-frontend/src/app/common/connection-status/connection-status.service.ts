import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConnectionStatusService {

  status: string = 'OK'

  constructor() { }

  setStatus(status: string ) {
    this.status = status
  }
}
