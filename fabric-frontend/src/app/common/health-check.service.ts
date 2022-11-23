import {Injectable} from '@angular/core';
import {SystemControllerService} from "@fabric/services";
import {TOAST_LEVEL, ToastService} from "./toast/toast.service";

@Injectable({
  providedIn: 'root'
})
export class HealthCheckService {

  constructor(private systemControllerService: SystemControllerService, private toastService: ToastService) {
    this.doHealthCheck();
  }

  doHealthCheck() {
    this.systemControllerService.checkSystemConfigUsingGet().subscribe((result) => {
      if (result && result.messages && result.messages.length > 0) {
        result.messages.forEach(message => this.toastService.add(TOAST_LEVEL.danger, message))
      }
    })
  }
}
