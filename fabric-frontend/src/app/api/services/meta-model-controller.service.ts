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

import { MetaModelId } from '../models/meta-model-id';


/**
 * Meta Model Controller
 */
@Injectable({
  providedIn: 'root',
})
export class MetaModelControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation createMetaModelUsingPost
   */
  static readonly CreateMetaModelUsingPostPath = '/api/metamodel';

  /**
   * createMetaModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createMetaModelUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createMetaModelUsingPost$Response(params?: {

    /**
     * environmentKey
     */
    environmentKey?: string;

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StrictHttpResponse<MetaModelId>> {

    const rb = new RequestBuilder(this.rootUrl, MetaModelControllerService.CreateMetaModelUsingPostPath, 'post');
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
        return r as StrictHttpResponse<MetaModelId>;
      })
    );
  }

  /**
   * createMetaModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createMetaModelUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createMetaModelUsingPost(params?: {

    /**
     * environmentKey
     */
    environmentKey?: string;

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<MetaModelId> {

    return this.createMetaModelUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<MetaModelId>) => r.body as MetaModelId)
    );
  }

}
