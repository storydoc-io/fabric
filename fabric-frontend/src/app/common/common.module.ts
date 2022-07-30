import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";

import {PopupMenuComponent} from './popup-menu/popup-menu.component';
import {ModalComponent} from './modal/modal.component'
import {Layout1ColComponent} from "./layout-1col/layout-1col.component";
import {BackButtonComponent} from "./back-button/back-button.component";
import {PanelComponent} from "./panel/panel.component";

@NgModule({
    declarations: [
        ModalComponent,
        PopupMenuComponent,
        Layout1ColComponent,
        BackButtonComponent,
        PanelComponent
    ],
    imports: [
        CoreModule,
    ],
    exports: [
        ModalComponent,
        PopupMenuComponent,
        Layout1ColComponent,
        BackButtonComponent,
        PanelComponent
    ]
})
export class FabricCommonModule {

}
