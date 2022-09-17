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

import { ConsoleDescriptorDto } from '../models/console-descriptor-dto';
import { ConsoleRequestDto } from '../models/console-request-dto';
import { ConsoleResponseItemDto } from '../models/console-response-item-dto';
import { SnippetDto } from '../models/snippet-dto';


/**
 * Console Controller
 */
@Injectable({
  providedIn: 'root',
})
export class ConsoleControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getDescriptorUsingGet
   */
  static readonly GetDescriptorUsingGetPath = '/api/console/descriptor';

  /**
   * getDescriptor.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getDescriptorUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDescriptorUsingGet$Response(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StrictHttpResponse<ConsoleDescriptorDto>> {

    const rb = new RequestBuilder(this.rootUrl, ConsoleControllerService.GetDescriptorUsingGetPath, 'get');
    if (params) {
      rb.query('systemComponentKey', params.systemComponentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ConsoleDescriptorDto>;
      })
    );
  }

  /**
   * getDescriptor.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getDescriptorUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDescriptorUsingGet(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<ConsoleDescriptorDto> {

    return this.getDescriptorUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<ConsoleDescriptorDto>) => r.body as ConsoleDescriptorDto)
    );
  }

  /**
   * Path part for operation runRequestUsingPost
   */
  static readonly RunRequestUsingPostPath = '/api/console/run';

  /**
   * runRequest.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `runRequestUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  runRequestUsingPost$Response(params?: {
    body?: ConsoleRequestDto
  }): Observable<StrictHttpResponse<ConsoleResponseItemDto>> {

    const rb = new RequestBuilder(this.rootUrl, ConsoleControllerService.RunRequestUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ConsoleResponseItemDto>;
      })
    );
  }

  /**
   * runRequest.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `runRequestUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  runRequestUsingPost(params?: {
    body?: ConsoleRequestDto
  }): Observable<ConsoleResponseItemDto> {

    return this.runRequestUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ConsoleResponseItemDto>) => r.body as ConsoleResponseItemDto)
    );
  }

  /**
   * Path part for operation getSnippetsUsingGet
   */
  static readonly GetSnippetsUsingGetPath = '/api/console/snippets';

  /**
   * getSnippets.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSnippetsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSnippetsUsingGet$Response(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StrictHttpResponse<Array<SnippetDto>>> {

    const rb = new RequestBuilder(this.rootUrl, ConsoleControllerService.GetSnippetsUsingGetPath, 'get');
    if (params) {
      rb.query('systemComponentKey', params.systemComponentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<SnippetDto>>;
      })
    );
  }

  /**
   * getSnippets.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getSnippetsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSnippetsUsingGet(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<Array<SnippetDto>> {

    return this.getSnippetsUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<SnippetDto>>) => r.body as Array<SnippetDto>)
    );
  }

}
