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

import { ExecutionDto } from '../models/execution-dto';


/**
 * Command Controller
 */
@Injectable({
  providedIn: 'root',
})
export class CommandControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getExecutionInfoUsingGet
   */
  static readonly GetExecutionInfoUsingGetPath = '/api/metamodel/info';

  /**
   * getExecutionInfo.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getExecutionInfoUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getExecutionInfoUsingGet$Response(params?: {
    id?: string;
  }): Observable<StrictHttpResponse<ExecutionDto>> {

    const rb = new RequestBuilder(this.rootUrl, CommandControllerService.GetExecutionInfoUsingGetPath, 'get');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ExecutionDto>;
      })
    );
  }

  /**
   * getExecutionInfo.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getExecutionInfoUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getExecutionInfoUsingGet(params?: {
    id?: string;
  }): Observable<ExecutionDto> {

    return this.getExecutionInfoUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<ExecutionDto>) => r.body as ExecutionDto)
    );
  }

}
