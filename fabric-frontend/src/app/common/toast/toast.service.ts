import {BehaviorSubject} from 'rxjs';
import {Injectable} from '@angular/core';

export const TOAST_LEVEL = {
    success: 'success-toast',
    warning: 'warning-toast',
    danger: 'danger-toast'
};

export type ToastMessage = {
    text: string
    level: string
}

@Injectable({
    providedIn: 'root'
})
export class ToastService {

    constructor() {
    }

    public toastMessages$: BehaviorSubject<ToastMessage[]> = new BehaviorSubject<ToastMessage[]>([]);

    add(level: string, text: string): void {
        let toastMessages = this.toastMessages$.value
        let toast = {level, text}
        toastMessages.push(toast)
        this.toastMessages$.next(toastMessages)
        setTimeout(() => {
            this.dismissToast(toast);
        }, 7000)
    }

    dismissToast(toast: ToastMessage): void {
      let toastMessages = this.toastMessages$.value
      toastMessages = toastMessages.filter(item => item != toast)
      this.toastMessages$.next(toastMessages)
    }

}