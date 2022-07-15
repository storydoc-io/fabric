import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";

import {PopupMenuComponent} from './popup-menu/popup-menu.component';
import {ModalComponent} from './modal/modal.component'

@NgModule({
  declarations: [
    ModalComponent,
    PopupMenuComponent
  ],
  imports: [
    CoreModule,
  ],
  exports: [
    ModalComponent,
    PopupMenuComponent
  ]
})
export class FabricCommonModule {

}
