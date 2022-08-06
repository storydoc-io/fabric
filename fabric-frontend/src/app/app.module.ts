import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {CoreModule} from "./core.module";

import { DashboardPageComponent } from './dashboard-page/dashboard-page.component';
import { SnapshotPageComponent } from './snapshot-page/snapshot-page.component';
import {FabricCommonModule} from "./common/common.module";
import { SnapshotDialogComponent } from './dashboard-page/snapshot-dialog/snapshot-dialog.component';
import { SystemDescriptionPageComponent } from './system-description-page/system-description-page.component';
import { MongoSnapshotComponent } from './snapshot-page/mongo-snapshot/mongo-snapshot.component';
import { MongoNavmodelPanelComponent } from './system-description-page/mongo-navmodel-panel/mongo-navmodel-panel.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardPageComponent,
    SnapshotPageComponent,
    SnapshotDialogComponent,
    SystemDescriptionPageComponent,
    MongoSnapshotComponent,
    MongoNavmodelPanelComponent,
  ],
  imports: [
    CoreModule,
    AppRoutingModule,
    FabricCommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
