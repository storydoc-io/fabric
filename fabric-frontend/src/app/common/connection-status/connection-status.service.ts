import {Injectable} from '@angular/core';
import {ConnectionStatus} from "../styleguide/status/status.component";
import {BehaviorSubject} from "rxjs";
import {TOAST_LEVEL, ToastService} from "../toast/toast.service";

@Injectable({
  providedIn: 'root'
})
export class ConnectionStatusService {

  status$ = new BehaviorSubject<ConnectionStatus>({ status: 'OK', msg: null} )

  constructor(private toastService: ToastService) { }

  setStatus(status: ConnectionStatus) {
    this.status$.next(status)
    this.showToast()
  }

  showToast() {
    let status = this.status$.value
    if (status.status==='Problem') {
      this.toastService.add(TOAST_LEVEL.danger, status.msg)
    }
  }

}
