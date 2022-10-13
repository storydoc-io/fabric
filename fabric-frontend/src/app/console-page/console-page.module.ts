import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {ConsolePageComponent} from "./console-page.component";
import {DataSourceSelectionPanelComponent} from './data-source-selection-panel/data-source-selection-panel.component';
import {ConsolePanelComponent} from './console-panel/console-panel.component';
import {SnippetPanelComponent} from './console-panel/snippet-panel/snippet-panel.component';
import {HistoryPanelComponent} from './console-panel/history-panel/history-panel.component';
import {SnippetDialogComponent} from './console-panel/snippet-dialog/snippet-dialog.component';
import {TableComponent} from './console-panel/table/table.component';
import { NavigationPanelComponent } from './console-panel/navigation-panel/navigation-panel.component';

@NgModule({
    declarations: [
        ConsolePageComponent,
        DataSourceSelectionPanelComponent,
        ConsolePanelComponent,
        SnippetPanelComponent,
        HistoryPanelComponent,
        SnippetDialogComponent,
        TableComponent,
        NavigationPanelComponent
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
