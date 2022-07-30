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

import { SnapshotDto } from '../models/snapshot-dto';
import { SnapshotId } from '../models/snapshot-id';
import { SnapshotSummaryDto } from '../models/snapshot-summary-dto';


/**
 * Snapshot Controller
 */
@Injectable({
  providedIn: 'root',
})
export class SnapshotControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getByIdUsingGet
   */
  static readonly GetByIdUsingGetPath = '/api/snaphot/snapshot';

  /**
   * getById.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getByIdUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getByIdUsingGet$Response(params?: {
    id?: string;
  }): Observable<StrictHttpResponse<SnapshotDto>> {

    const rb = new RequestBuilder(this.rootUrl, SnapshotControllerService.GetByIdUsingGetPath, 'get');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SnapshotDto>;
      })
    );
  }

  /**
   * getById.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getByIdUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getByIdUsingGet(params?: {
    id?: string;
  }): Observable<SnapshotDto> {

    return this.getByIdUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SnapshotDto>) => r.body as SnapshotDto)
    );
  }

  /**
   * Path part for operation createUsingPost
   */
  static readonly CreateUsingPostPath = '/api/snaphot/snapshot';

  /**
   * create.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createUsingPost$Response(params?: {

    /**
     * environment
     */
    environment?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<SnapshotId>> {

    const rb = new RequestBuilder(this.rootUrl, SnapshotControllerService.CreateUsingPostPath, 'post');
    if (params) {
      rb.query('environment', params.environment, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SnapshotId>;
      })
    );
  }

  /**
   * create.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createUsingPost(params?: {

    /**
     * environment
     */
    environment?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<SnapshotId> {

    return this.createUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<SnapshotId>) => r.body as SnapshotId)
    );
  }

  /**
   * Path part for operation listUsingGet
   */
  static readonly ListUsingGetPath = '/api/snaphot/snapshots';

  /**
   * list.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `listUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  listUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<Array<SnapshotSummaryDto>>> {

    const rb = new RequestBuilder(this.rootUrl, SnapshotControllerService.ListUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<SnapshotSummaryDto>>;
      })
    );
  }

  /**
   * list.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `listUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  listUsingGet(params?: {
  }): Observable<Array<SnapshotSummaryDto>> {

    return this.listUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<SnapshotSummaryDto>>) => r.body as Array<SnapshotSummaryDto>)
    );
  }

}
