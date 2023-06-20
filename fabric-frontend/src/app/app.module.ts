import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {CoreModule} from './core.module';
import {FabricCommonModule} from './common/common.module';

import {SystemDescriptionModule} from './settings/system-description.module';
import {ConsolePageModule} from './console-page/console-page.module';
import {SnapshotModule} from "./snapshot/snapshot.module";
import {NavigationModule} from "./navigation-page/navigation.module"
import {ComponentModule} from "./component/component.module";
import {QueryModule} from "./query/query.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CoreModule,
    AppRoutingModule,
    FabricCommonModule,
    ComponentModule,
    SystemDescriptionModule,
    QueryModule,
    ConsolePageModule,
    SnapshotModule,
    NavigationModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
