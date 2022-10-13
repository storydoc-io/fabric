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

import { ExecutionId } from '../models/execution-id';
import { SnapshotDto } from '../models/snapshot-dto';
import { SnapshotDescriptorDto } from '../models/snapshot-descriptor-dto';
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
   * Path part for operation deleteByIdUsingDelete
   */
  static readonly DeleteByIdUsingDeletePath = '/api/snaphot/delete';

  /**
   * deleteById.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteByIdUsingDelete()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteByIdUsingDelete$Response(params?: {
    id?: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, SnapshotControllerService.DeleteByIdUsingDeletePath, 'delete');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
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
   * deleteById.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteByIdUsingDelete$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteByIdUsingDelete(params?: {
    id?: string;
  }): Observable<void> {

    return this.deleteByIdUsingDelete$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
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
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createUsingPost$Response(params?: {
    body?: SnapshotDescriptorDto
  }): Observable<StrictHttpResponse<SnapshotId>> {

    const rb = new RequestBuilder(this.rootUrl, SnapshotControllerService.CreateUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
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
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createUsingPost(params?: {
    body?: SnapshotDescriptorDto
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

  /**
   * Path part for operation uploadUsingPost
   */
  static readonly UploadUsingPostPath = '/api/snaphot/upload';

  /**
   * upload.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  uploadUsingPost$Response(params?: {
    id?: string;

    /**
     * environmentKey
     */
    environmentKey?: string;
  }): Observable<StrictHttpResponse<ExecutionId>> {

    const rb = new RequestBuilder(this.rootUrl, SnapshotControllerService.UploadUsingPostPath, 'post');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
      rb.query('environmentKey', params.environmentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ExecutionId>;
      })
    );
  }

  /**
   * upload.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `uploadUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  uploadUsingPost(params?: {
    id?: string;

    /**
     * environmentKey
     */
    environmentKey?: string;
  }): Observable<ExecutionId> {

    return this.uploadUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ExecutionId>) => r.body as ExecutionId)
    );
  }

}
