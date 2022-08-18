import {ModalService} from "../modal/modal-service";
import {ConfirmationDialogSpec} from "./confirmation-dialog.component";

export abstract class HasConfirmationDialogMixin {  // todo make this a proper mixin

    protected constructor(protected modalService: ModalService) { }

    // confirmation dialog

    confirmationDialogSpec: any;

    abstract confirmationDialogId(): string

    openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
        this.confirmationDialogSpec = confirmationDialogSpec
        this.modalService.open(this.confirmationDialogId())
    }

    closeConfirmationDialog() {
        this.modalService.close(this.confirmationDialogId())
    }


}
