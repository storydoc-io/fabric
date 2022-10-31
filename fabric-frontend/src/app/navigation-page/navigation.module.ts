import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {NavigationPageComponent} from "./navigation-page.component";
import {NavigationPanelComponent} from "./navigation-panel/navigation-panel.component";
import {NavigationSelectionPanelComponent} from './navigation-selection-panel/navigation-selection-panel.component';
import {NavigationTreeComponent} from './navigation-tree/navigation-tree.component';
import {NavigationTreeItemComponent} from './navigation-tree-item/navigation-tree-item.component';
import {ComponentModule} from "../component/component.module";
import {FormsModule} from "@angular/forms";

@NgModule({
    declarations: [
        NavigationPageComponent,
        NavigationPanelComponent,
        NavigationSelectionPanelComponent,
        NavigationTreeComponent,
        NavigationTreeItemComponent
    ],
    imports: [
        CoreModule,
        FabricCommonModule,
        ComponentModule,
        FormsModule
    ],
    exports: [
        NavigationPageComponent
    ]
})
export class NavigationModule {}