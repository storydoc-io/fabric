import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";

import {PopupMenuComponent} from './popup-menu/popup-menu.component';
import {ModalComponent} from './modal/modal.component'
import {Layout1ColComponent} from "./styleguide/layout-1col/layout-1col.component";
import {BackButtonComponent} from "./styleguide/back-button/back-button.component";
import {PanelComponent} from "./styleguide/panel/panel.component";
import {ConfirmationDialogComponent} from "./confirmation-dialog/confirmation-dialog.component";
import {ValidationMessagesComponent} from './validation-messages/validation-messages.component';
import {TitleComponent} from './styleguide/title/title.component';
import {BreadcrumbComponent} from './styleguide/breadcrumb/breadcrumb.component';
import {ActionButtonsComponent} from './styleguide/action-buttons/action-buttons.component';
import {SideBarComponent} from './styleguide/side-bar/side-bar.component';
import {ModalHeaderComponent} from './styleguide/modal-header/modal-header.component';

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
        SideBarComponent,
        ModalHeaderComponent,
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
        SideBarComponent,
        ModalHeaderComponent,
    ]
})
export class FabricCommonModule {

}
