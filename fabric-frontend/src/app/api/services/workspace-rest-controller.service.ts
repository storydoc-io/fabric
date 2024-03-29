/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { WorkspaceSettings } from '../models/workspace-settings';


/**
 * Workspace Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class WorkspaceRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getSettingsUsingGet
   */
  static readonly GetSettingsUsingGetPath = '/api/settings/settings';

  /**
   * getSettings.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSettingsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSettingsUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<WorkspaceSettings>> {

    const rb = new RequestBuilder(this.rootUrl, WorkspaceRestControllerService.GetSettingsUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<WorkspaceSettings>;
      })
    );
  }

  /**
   * getSettings.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getSettingsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSettingsUsingGet(params?: {
  }): Observable<WorkspaceSettings> {

    return this.getSettingsUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<WorkspaceSettings>) => r.body as WorkspaceSettings)
    );
  }

}
