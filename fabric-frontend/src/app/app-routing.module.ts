import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardPageComponent} from "./dashboard-page/dashboard-page.component";
import {SnapshotPageComponent} from "./snapshot-page/snapshot-page.component";
import {SystemDescriptionPageComponent} from "./system-description-page/system-description-page.component";


const routes: Routes = [
  {path: '', redirectTo: '/fe/dashboard', pathMatch: 'full'},
  {path: 'fe/dashboard', component: DashboardPageComponent},
  {path: 'fe/systemdescription', component: SystemDescriptionPageComponent},
  {path: 'fe/snapshot/:snapshotId', component: SnapshotPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
