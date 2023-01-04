import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';
import {SnapshotPageComponent} from './snapshot-page/snapshot-page.component';
import {MetaModelPageComponent} from "./system-description-page/meta-model-page/meta-model-page.component";
import {ConsolePageComponent} from './console-page/console-page.component';
import {DummyPageComponent} from './dashboard-page/dummy-page/dummy-page.component';
import {NavigationPageComponent} from "./navigation-page/navigation-page.component";
import {SettingsPageComponent} from "./system-description-page/settings-page/settings-page.component";
import {DataSourcesPageComponent} from "./system-description-page/data-sources-page/data-sources-page.component";
import {EnvironmentsPageComponent} from "./system-description-page/environments-page/environments-page.component";


const routes: Routes = [
  {path: '', redirectTo: '/fe/console', pathMatch: 'full'},
  {path: 'fe/dashboard', component: DashboardPageComponent},
  {path: 'fe/console', component: ConsolePageComponent},
  {path: 'fe/navigation', component: NavigationPageComponent},
  {path: 'fe/datasources', component: DataSourcesPageComponent},
  {path: 'fe/datasources/:systemComponentKey', component: DataSourcesPageComponent},
  {path: 'fe/datasources/:systemComponentKey/metamodel', component: MetaModelPageComponent},
  {path: 'fe/datasources/:systemComponentKey/settings', component: SettingsPageComponent},
  {path: 'fe/environments', component: EnvironmentsPageComponent},
  {path: 'fe/snapshot/:snapshotId', component: SnapshotPageComponent},
  {path: 'fe/dummy/:executionId', component: DummyPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
