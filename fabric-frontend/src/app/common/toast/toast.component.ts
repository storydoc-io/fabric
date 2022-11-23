import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {faExclamationTriangle} from "@fortawesome/free-solid-svg-icons";
import {ToastMessage, ToastService} from './toast.service';

// based on https://dev.to/riapacheco/custom-reusable-toast-component-with-angular-animations-async-pipe-and-rxjs-behaviorsubject-2bdf

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss'],
  animations: [
    trigger('toastTrigger', [
      state('open', style({ transform: 'translateY(200%)', opacity: 100 })),
      state('close', style({ transform: 'translateY(-200%)', opacity: 0 })),
      transition('open <=> close', [
        animate('300ms ease-in-out')
      ])
    ])
  ]
})
export class ToastComponent implements OnInit {
  toastClass!: string[];
  toastMessage!: string;
  showsToast!: boolean;

  faExclamationTriangle = faExclamationTriangle

  constructor(public toast: ToastService) { }

  ngOnInit(): void {
  }

  dismiss(toast: ToastMessage): void {
    this.toast.dismissToast(toast);
  }

}