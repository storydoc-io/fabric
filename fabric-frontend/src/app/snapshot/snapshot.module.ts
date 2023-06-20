import {NgModule} from "@angular/core";
import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";
import {SnapshotsPageComponent} from "./snapshots-page/snapshots-page.component";
import {SnapshotPageComponent} from "./snapshot-page/snapshot-page.component";
import {SnapshotDialogComponent} from "./snapshots-page/snapshot-dialog/snapshot-dialog.component";
import {MongoSnapshotComponent} from "./snapshot-page/mongo-snapshot/mongo-snapshot.component";
import {SnapshotOverviewPanelComponent} from './snapshots-page/snapshot-overview-panel/snapshot-overview-panel.component';
import {SnapshotUploadDialogComponent} from './snapshots-page/snapshot-upload-dialog/snapshot-upload-dialog.component';
import {DummyPageComponent} from './snapshots-page/dummy-page/dummy-page.component';


@NgModule({
    declarations: [
        SnapshotsPageComponent,
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
        SnapshotsPageComponent,
        SnapshotPageComponent,
    ]
})
export class SnapshotModule { }
