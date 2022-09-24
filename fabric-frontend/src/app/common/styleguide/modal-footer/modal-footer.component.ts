import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'app-modal-footer',
    templateUrl: './modal-footer.component.html',
    styleUrls: ['./modal-footer.component.scss']
})
export class ModalFooterComponent {

    @Input()
    confirmDisabled: boolean = false

    @Input()
    confirmText: string = 'Save changes'

    @Input()
    cancelText: string = 'Cancel'

    @Output()
    onConfirm = new EventEmitter<void>()

    @Output()
    onCancel = new EventEmitter<void>()

    constructor() {
    }

    confirm() {
        this.onConfirm.emit()
    }

    cancel() {
        this.onCancel.emit()
    }

}
