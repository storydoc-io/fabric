import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {NavigationPageComponent} from "./navigation-page.component";
import {NavigationPanelComponent} from "./navigation-panel/navigation-panel.component";
import {NavigationSelectionPanelComponent} from './navigation-selection-panel/navigation-selection-panel.component';
import { NavigationTreeComponent } from './navigation-tree/navigation-tree.component';
import { NavigationTreeItemComponent } from './navigation-tree-item/navigation-tree-item.component';

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
    ],
    exports: [
        NavigationPageComponent
    ]
})
export class NavigationModule {}