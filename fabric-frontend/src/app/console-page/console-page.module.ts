import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {ConsolePageComponent} from "./console-page.component";
import { DataSourceSelectionPanelComponent } from './data-source-selection-panel/data-source-selection-panel.component';
import { ConsolePanelComponent } from './console-panel/console-panel.component';
import { SnippetPanelComponent } from './snippet-panel/snippet-panel.component';

@NgModule({
    declarations: [
        ConsolePageComponent,
        DataSourceSelectionPanelComponent,
        ConsolePanelComponent,
        SnippetPanelComponent
    ],
    imports: [
        CoreModule,
        FabricCommonModule
    ],
    exports: [
        ConsolePageComponent
    ]
})
export class ConsolePageModule { }
