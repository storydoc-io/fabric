import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardPageComponent} from './dashboard-page/dashboard-page.component';
import {SnapshotPageComponent} from './snapshot-page/snapshot-page.component';
import {SystemDescriptionPageComponent} from './system-description-page/system-description-page.component';
import {ConsolePageComponent} from './console-page/console-page.component';
import {DummyPageComponent} from './dashboard-page/dummy-page/dummy-page.component';
import {NavigationPageComponent} from "./navigation-page/navigation-page.component";


const routes: Routes = [
  {path: '', redirectTo: '/fe/dashboard', pathMatch: 'full'},
  {path: 'fe/dashboard', component: DashboardPageComponent},
  {path: 'fe/console', component: ConsolePageComponent},
  {path: 'fe/navigation', component: NavigationPageComponent},
  {path: 'fe/systemdescription', component: SystemDescriptionPageComponent},
  {path: 'fe/snapshot/:snapshotId', component: SnapshotPageComponent},
  {path: 'fe/dummy/:executionId', component: DummyPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
