/* tslint:disable */
/* eslint-disable */
import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { ConnectionControllerService } from './services/connection-controller.service';
import { MetaModelControllerService } from './services/meta-model-controller.service';
import { MongoSnapshotControllerService } from './services/mongo-snapshot-controller.service';
import { SnapshotControllerService } from './services/snapshot-controller.service';
import { SystemDescriptionControllerService } from './services/system-description-controller.service';
import { WorkspaceRestControllerService } from './services/workspace-rest-controller.service';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    ConnectionControllerService,
    MetaModelControllerService,
    MongoSnapshotControllerService,
    SnapshotControllerService,
    SystemDescriptionControllerService,
    WorkspaceRestControllerService,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}
