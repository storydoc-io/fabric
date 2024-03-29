import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {ConsolePageComponent} from "./console-page.component";
import {ConsolePanelComponent} from './console-panel/console-panel.component';
import {SnippetPanelComponent} from './console-panel/snippet-panel/snippet-panel.component';
import {HistoryPanelComponent} from './console-panel/history-panel/history-panel.component';
import {SnippetDialogComponent} from './console-panel/snippet-dialog/snippet-dialog.component';
import {TableComponent} from './console-panel/table/table.component';
import {ComponentModule} from "../component/component.module";
import {QueryPanelComponent} from './console-panel/query-panel/query-panel.component';
import { DocumentsComponent } from './console-panel/documents/documents.component';

@NgModule({
    declarations: [
        ConsolePageComponent,
        ConsolePanelComponent,
        SnippetPanelComponent,
        HistoryPanelComponent,
        SnippetDialogComponent,
        TableComponent,
        QueryPanelComponent,
        DocumentsComponent,
    ],
    imports: [
        CoreModule,
        FabricCommonModule,
        ComponentModule
    ],
    exports: [
        ConsolePageComponent
    ]
})
export class ConsolePageModule { }
