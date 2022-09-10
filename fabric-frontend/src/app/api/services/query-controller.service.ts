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

import { QueryRequestDto } from '../models/query-request-dto';
import { QueryResponseItemDto } from '../models/query-response-item-dto';


/**
 * Query Controller
 */
@Injectable({
  providedIn: 'root',
})
export class QueryControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation doQueryUsingPost
   */
  static readonly DoQueryUsingPostPath = '/api/query/run';

  /**
   * doQuery.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `doQueryUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  doQueryUsingPost$Response(params?: {
    body?: QueryRequestDto
  }): Observable<StrictHttpResponse<QueryResponseItemDto>> {

    const rb = new RequestBuilder(this.rootUrl, QueryControllerService.DoQueryUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<QueryResponseItemDto>;
      })
    );
  }

  /**
   * doQuery.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `doQueryUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  doQueryUsingPost(params?: {
    body?: QueryRequestDto
  }): Observable<QueryResponseItemDto> {

    return this.doQueryUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<QueryResponseItemDto>) => r.body as QueryResponseItemDto)
    );
  }

}
