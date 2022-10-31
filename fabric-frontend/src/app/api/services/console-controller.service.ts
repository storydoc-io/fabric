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
import { NavItem } from '../models/nav-item';
import { NavigationRequest } from '../models/navigation-request';
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
   * Path part for operation getNavigationUsingPost
   */
  static readonly GetNavigationUsingPostPath = '/api/console/navigation';

  /**
   * getNavigation.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getNavigationUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  getNavigationUsingPost$Response(params?: {
    body?: NavigationRequest
  }): Observable<StrictHttpResponse<Array<NavItem>>> {

    const rb = new RequestBuilder(this.rootUrl, ConsoleControllerService.GetNavigationUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<NavItem>>;
      })
    );
  }

  /**
   * getNavigation.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getNavigationUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  getNavigationUsingPost(params?: {
    body?: NavigationRequest
  }): Observable<Array<NavItem>> {

    return this.getNavigationUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<Array<NavItem>>) => r.body as Array<NavItem>)
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
   * Path part for operation createSnippetUsingPost
   */
  static readonly CreateSnippetUsingPostPath = '/api/console/snippet';

  /**
   * createSnippet.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSnippetUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSnippetUsingPost$Response(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
    body?: SnippetDto
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, ConsoleControllerService.CreateSnippetUsingPostPath, 'post');
    if (params) {
      rb.query('systemComponentKey', params.systemComponentKey, {"style":"form"});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
      })
    );
  }

  /**
   * createSnippet.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createSnippetUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createSnippetUsingPost(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
    body?: SnippetDto
  }): Observable<void> {

    return this.createSnippetUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
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