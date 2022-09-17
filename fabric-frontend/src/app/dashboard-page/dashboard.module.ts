import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {DashboardPageComponent} from "./dashboard-page.component";
import {SnapshotPageComponent} from "../snapshot-page/snapshot-page.component";
import {SnapshotDialogComponent} from "./snapshot-dialog/snapshot-dialog.component";
import {MongoSnapshotComponent} from "../snapshot-page/mongo-snapshot/mongo-snapshot.component";
import { SnapshotOverviewPanelComponent } from './snapshot-overview-panel/snapshot-overview-panel.component';
import { SnapshotUploadDialogComponent } from './snapshot-upload-dialog/snapshot-upload-dialog.component';
import { DummyPageComponent } from './dummy-page/dummy-page.component';


@NgModule({
    declarations: [
        DashboardPageComponent,
        SnapshotPageComponent,
        SnapshotDialogComponent,
        MongoSnapshotComponent,
        SnapshotOverviewPanelComponent,
        SnapshotUploadDialogComponent,
        DummyPageComponent,
    ],
    imports: [
        CoreModule,
        FabricCommonModule
    ],
    exports: [
        DashboardPageComponent,
        SnapshotPageComponent,
    ]
})
export class DashboardModule { }
