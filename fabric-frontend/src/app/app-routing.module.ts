import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SnapshotsPageComponent} from './snapshot/snapshots-page/snapshots-page.component';
import {SnapshotPageComponent} from './snapshot/snapshot-page/snapshot-page.component';
import {MetaModelPageComponent} from "./settings/meta-model-page/meta-model-page.component";
import {ConsolePageComponent} from './console-page/console-page.component';
import {DummyPageComponent} from './snapshot/snapshots-page/dummy-page/dummy-page.component';
import {NavigationPageComponent} from "./navigation-page/navigation-page.component";
import {ConnectionSettingsPageComponent} from "./settings/connection-settings-page/connection-settings-page.component";
import {DataSourcesPageComponent} from "./settings/data-sources-page/data-sources-page.component";
import {EnvironmentsPageComponent} from "./settings/environments-page/environments-page.component";
import {QueryPageComponent} from "./query/query-page/query-page.component";


const routes: Routes = [
  {path: '', redirectTo: '/fe/console', pathMatch: 'full'},
  {path: 'fe/snapshots', component: SnapshotsPageComponent},
  {path: 'fe/console', component: ConsolePageComponent},
  {path: 'fe/navigation', component: NavigationPageComponent},
  {path: 'fe/datasources', component: DataSourcesPageComponent},
  {path: 'fe/datasources/:systemComponentKey', component: DataSourcesPageComponent},
  {path: 'fe/datasources/:systemComponentKey/metamodel', component: MetaModelPageComponent},
  {path: 'fe/datasources/:systemComponentKey/settings', component: ConnectionSettingsPageComponent},
  {path: 'fe/environments', component: EnvironmentsPageComponent},
  {path: 'fe/snapshot/:snapshotId', component: SnapshotPageComponent},
  {path: 'fe/dummy/:executionId', component: DummyPageComponent},
  {path: 'fe/query/:queryId', component: QueryPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
