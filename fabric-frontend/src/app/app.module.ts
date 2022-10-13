import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {CoreModule} from './core.module';
import {FabricCommonModule} from './common/common.module';

import {SystemDescriptionModule} from './system-description-page/system-description.module';
import {ConsolePageModule} from './console-page/console-page.module';
import {DashboardModule} from "./dashboard-page/dashboard.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CoreModule,
    AppRoutingModule,
    FabricCommonModule,
    SystemDescriptionModule,
    ConsolePageModule,
    DashboardModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
