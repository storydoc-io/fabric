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

import { MongoMetaModel } from '../models/mongo-meta-model';
import { MongoNavigationModel } from '../models/mongo-navigation-model';
import { MongoSnapshot } from '../models/mongo-snapshot';


/**
 * Mongo Snapshot Controller
 */
@Injectable({
  providedIn: 'root',
})
export class MongoSnapshotControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getMetaModelUsingGet
   */
  static readonly GetMetaModelUsingGetPath = '/api/mongo/metamodel';

  /**
   * getMetaModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMetaModelUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMetaModelUsingGet$Response(params?: {

    /**
     * environmentKey
     */
    environmentKey?: string;

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StrictHttpResponse<MongoMetaModel>> {

    const rb = new RequestBuilder(this.rootUrl, MongoSnapshotControllerService.GetMetaModelUsingGetPath, 'get');
    if (params) {
      rb.query('environmentKey', params.environmentKey, {"style":"form"});
      rb.query('systemComponentKey', params.systemComponentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<MongoMetaModel>;
      })
    );
  }

  /**
   * getMetaModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getMetaModelUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMetaModelUsingGet(params?: {

    /**
     * environmentKey
     */
    environmentKey?: string;

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<MongoMetaModel> {

    return this.getMetaModelUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<MongoMetaModel>) => r.body as MongoMetaModel)
    );
  }

  /**
   * Path part for operation getNavigationModelUsingGet
   */
  static readonly GetNavigationModelUsingGetPath = '/api/mongo/navigationmodel';

  /**
   * getNavigationModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getNavigationModelUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getNavigationModelUsingGet$Response(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StrictHttpResponse<MongoNavigationModel>> {

    const rb = new RequestBuilder(this.rootUrl, MongoSnapshotControllerService.GetNavigationModelUsingGetPath, 'get');
    if (params) {
      rb.query('systemComponentKey', params.systemComponentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<MongoNavigationModel>;
      })
    );
  }

  /**
   * getNavigationModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getNavigationModelUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getNavigationModelUsingGet(params?: {

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<MongoNavigationModel> {

    return this.getNavigationModelUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<MongoNavigationModel>) => r.body as MongoNavigationModel)
    );
  }

  /**
   * Path part for operation getMongoSnapshotUsingGet
   */
  static readonly GetMongoSnapshotUsingGetPath = '/api/mongo/snapshot';

  /**
   * getMongoSnapshot.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMongoSnapshotUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMongoSnapshotUsingGet$Response(params?: {
    id?: string;

    /**
     * componentKey
     */
    componentKey?: string;
  }): Observable<StrictHttpResponse<MongoSnapshot>> {

    const rb = new RequestBuilder(this.rootUrl, MongoSnapshotControllerService.GetMongoSnapshotUsingGetPath, 'get');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
      rb.query('componentKey', params.componentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<MongoSnapshot>;
      })
    );
  }

  /**
   * getMongoSnapshot.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getMongoSnapshotUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMongoSnapshotUsingGet(params?: {
    id?: string;

    /**
     * componentKey
     */
    componentKey?: string;
  }): Observable<MongoSnapshot> {

    return this.getMongoSnapshotUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<MongoSnapshot>) => r.body as MongoSnapshot)
    );
  }

}
