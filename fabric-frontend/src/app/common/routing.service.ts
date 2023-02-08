import {Injectable} from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RoutingService {

  constructor(private router: Router) { }

  public homeRoute() {
    return ['/'];
  }

  public consolePageRoute() {
    return ['/', 'fe', 'console'];
  }

  public dataSourcesPageRoute() {
    return ['/', 'fe', 'datasources'];
  }

  public dataSourcesPageRouteWithSelection(systemComponentKey: string) {
    return ['/', 'fe', 'datasources', systemComponentKey];
  }

  public metaModelPageRoute(systemComponentKey: string) {
    return ['/', 'fe', 'datasources', systemComponentKey, 'metamodel'];
  }

  public connectionSettingsRoute(systemComponentKey: string) {
    return ['fe', 'datasources', systemComponentKey, 'settings'];
  }

  public environmentsPageRoute() {
    return ['/', 'fe', 'environments'];
  }

  public snapshotsPageroute() {
    return ['/', 'fe', 'snapshots'];
  }

  public navigateToMetaModelPage(systemComponentKey: string) {
    this.router.navigate(this.metaModelPageRoute(systemComponentKey))
  }

  public navigateToEnvironmentsPage() {
    this.router.navigate(this.environmentsPageRoute())
  }

  public navigateToDataSourcesPage() {
    this.router.navigate(this.dataSourcesPageRoute())
  }

  public navigateToConnectionSettingsPage(systemComponentKey: string) {
    this.router.navigate(this.connectionSettingsRoute(systemComponentKey))
  }

}
