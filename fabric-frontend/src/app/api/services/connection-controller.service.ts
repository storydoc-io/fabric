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

import { ConnectionTestRequestDto } from '../models/connection-test-request-dto';
import { ConnectionTestResponseDto } from '../models/connection-test-response-dto';


/**
 * Connection Controller
 */
@Injectable({
  providedIn: 'root',
})
export class ConnectionControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation testConnectionUsingPost
   */
  static readonly TestConnectionUsingPostPath = '/api/connection';

  /**
   * testConnection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `testConnectionUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  testConnectionUsingPost$Response(params?: {
    body?: ConnectionTestRequestDto
  }): Observable<StrictHttpResponse<ConnectionTestResponseDto>> {

    const rb = new RequestBuilder(this.rootUrl, ConnectionControllerService.TestConnectionUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ConnectionTestResponseDto>;
      })
    );
  }

  /**
   * testConnection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `testConnectionUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  testConnectionUsingPost(params?: {
    body?: ConnectionTestRequestDto
  }): Observable<ConnectionTestResponseDto> {

    return this.testConnectionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ConnectionTestResponseDto>) => r.body as ConnectionTestResponseDto)
    );
  }

}
