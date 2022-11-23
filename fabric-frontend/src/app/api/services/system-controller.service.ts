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

import { SystemCheckResultDto } from '../models/system-check-result-dto';


/**
 * System Controller
 */
@Injectable({
  providedIn: 'root',
})
export class SystemControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation checkSystemConfigUsingGet
   */
  static readonly CheckSystemConfigUsingGetPath = '/api/system/checkConfig';

  /**
   * checkSystemConfig.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `checkSystemConfigUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  checkSystemConfigUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<SystemCheckResultDto>> {

    const rb = new RequestBuilder(this.rootUrl, SystemControllerService.CheckSystemConfigUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SystemCheckResultDto>;
      })
    );
  }

  /**
   * checkSystemConfig.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `checkSystemConfigUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  checkSystemConfigUsingGet(params?: {
  }): Observable<SystemCheckResultDto> {

    return this.checkSystemConfigUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SystemCheckResultDto>) => r.body as SystemCheckResultDto)
    );
  }

}
