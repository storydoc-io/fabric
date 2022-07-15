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

import { SystemDescriptionDto } from '../models/system-description-dto';


/**
 * System Description Controller
 */
@Injectable({
  providedIn: 'root',
})
export class SystemDescriptionControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getSystemDescriptionUsingGet
   */
  static readonly GetSystemDescriptionUsingGetPath = '/api/systemdescription';

  /**
   * getSystemDescription.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSystemDescriptionUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSystemDescriptionUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<SystemDescriptionDto>> {

    const rb = new RequestBuilder(this.rootUrl, SystemDescriptionControllerService.GetSystemDescriptionUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SystemDescriptionDto>;
      })
    );
  }

  /**
   * getSystemDescription.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getSystemDescriptionUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSystemDescriptionUsingGet(params?: {
  }): Observable<SystemDescriptionDto> {

    return this.getSystemDescriptionUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SystemDescriptionDto>) => r.body as SystemDescriptionDto)
    );
  }

}
