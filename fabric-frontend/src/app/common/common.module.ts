import {NgModule} from "@angular/core";

import {CoreModule} from '../core.module';

import {PopupMenuComponent} from './popup-menu/popup-menu.component';
import {ModalComponent} from './modal/modal.component';
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
import {ModalFooterComponent} from './styleguide/modal-footer/modal-footer.component';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {HttpErrorInterceptor} from "./connection-status/http-error.interceptor";
import {ConnectionStatusComponent} from './connection-status/connection-status.component';
import {CommandProgressComponent} from './styleguide/command-progress/command-progress.component';
import {StatusComponent} from './styleguide/status/status.component';
import {NavComponent} from './styleguide/nav/nav.component';
import {IntroComponent} from './styleguide/intro/intro.component';
import {LoadingComponent} from './loading/loading.component';
import {ToastComponent} from './toast/toast.component';
import {InfoComponent} from './styleguide/info/info.component';
import {PagerComponent} from './pager/pager.component';
import {VarDirective} from "./ng-var.directive";

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
        ModalFooterComponent,
        ConnectionStatusComponent,
        CommandProgressComponent,
        StatusComponent,
        NavComponent,
        IntroComponent,
        LoadingComponent,
        ToastComponent,
        InfoComponent,
        PagerComponent,
        VarDirective
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpErrorInterceptor,
            multi: true
        }
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
        ModalFooterComponent,
        CommandProgressComponent,
        NavComponent,
        IntroComponent,
        LoadingComponent,
        InfoComponent,
        PagerComponent,
        VarDirective
    ]
})
export class FabricCommonModule {

}
