import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";

import {PopupMenuComponent} from './popup-menu/popup-menu.component';
import {ModalComponent} from './modal/modal.component'
import {Layout1ColComponent} from "./layout/layout-1col/layout-1col.component";
import {BackButtonComponent} from "./back-button/back-button.component";
import {PanelComponent} from "./panel/panel.component";
import {ConfirmationDialogComponent} from "./confirmation-dialog/confirmation-dialog.component";
import {ValidationMessagesComponent} from './validation-messages/validation-messages.component';
import {TitleComponent} from './layout/title/title.component';
import {BreadcrumbComponent} from './layout/breadcrumb/breadcrumb.component';
import {ActionButtonsComponent} from './grid/action-buttons/action-buttons.component';

@NgModule({
    declarations: [
        ModalComponent,
        PopupMenuComponent,
        Layout1ColComponent,
        BackButtonComponent,
        PanelComponent,
        ConfirmationDialogComponent,
        ValidationMessagesComponent,
        TitleComponent,
        BreadcrumbComponent,
        ActionButtonsComponent,
    ],
    imports: [
        CoreModule,
    ],
    exports: [
        ModalComponent,
        PopupMenuComponent,
        Layout1ColComponent,
        BackButtonComponent,
        PanelComponent,
        ConfirmationDialogComponent,
        ValidationMessagesComponent,
        TitleComponent,
        BreadcrumbComponent,
        ActionButtonsComponent,
    ]
})
export class FabricCommonModule {

}
