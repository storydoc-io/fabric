import {Component, OnInit} from '@angular/core';
import {SystemDescriptionService} from "./system-description.service";
import {ModalService} from "../common/modal/modal-service";
import {ConfirmationDialogSpec} from "../common/confirmation-dialog/confirmation-dialog.component";


@Component({
    selector: 'app-system-description-page',
    templateUrl: './system-description-page.component.html',
    styleUrls: ['./system-description-page.component.scss']
})
export class SystemDescriptionPageComponent implements OnInit {

    constructor(private modalService: ModalService, private service: SystemDescriptionService) {
    }

    systemDescription$ = this.service.systemDescription$

    ngOnInit(): void {
    }


    // confirmation dialog

    confirmationDialogSpec: any;

    confirmationDialogId(): string {
        return 'confirmation-dialog'
    }

    openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
        this.confirmationDialogSpec = confirmationDialogSpec
        this.modalService.open(this.confirmationDialogId())
    }

    closeConfirmationDialog() {
        this.modalService.close(this.confirmationDialogId())
    }

}
