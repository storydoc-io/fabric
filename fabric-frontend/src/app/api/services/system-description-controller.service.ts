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

import { StructureDto } from '../models/structure-dto';
import { SystemDescriptionDto } from '../models/system-description-dto';
import { SystemTypeDescriptorDto } from '../models/system-type-descriptor-dto';


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

  /**
   * Path part for operation setSystemDescriptionUsingPost
   */
  static readonly SetSystemDescriptionUsingPostPath = '/api/systemdescription';

  /**
   * setSystemDescription.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setSystemDescriptionUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  setSystemDescriptionUsingPost$Response(params?: {
    body?: SystemDescriptionDto
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, SystemDescriptionControllerService.SetSystemDescriptionUsingPostPath, 'post');
    if (params) {
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
   * setSystemDescription.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `setSystemDescriptionUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  setSystemDescriptionUsingPost(params?: {
    body?: SystemDescriptionDto
  }): Observable<void> {

    return this.setSystemDescriptionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation getSystemComponentEnvironmentStructureUsingGet
   */
  static readonly GetSystemComponentEnvironmentStructureUsingGetPath = '/api/systemdescription/structure/';

  /**
   * getSystemComponentEnvironmentStructure.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSystemComponentEnvironmentStructureUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSystemComponentEnvironmentStructureUsingGet$Response(params?: {

    /**
     * envKey
     */
    envKey?: string;

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StrictHttpResponse<StructureDto>> {

    const rb = new RequestBuilder(this.rootUrl, SystemDescriptionControllerService.GetSystemComponentEnvironmentStructureUsingGetPath, 'get');
    if (params) {
      rb.query('envKey', params.envKey, {"style":"form"});
      rb.query('systemComponentKey', params.systemComponentKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<StructureDto>;
      })
    );
  }

  /**
   * getSystemComponentEnvironmentStructure.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getSystemComponentEnvironmentStructureUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSystemComponentEnvironmentStructureUsingGet(params?: {

    /**
     * envKey
     */
    envKey?: string;

    /**
     * systemComponentKey
     */
    systemComponentKey?: string;
  }): Observable<StructureDto> {

    return this.getSystemComponentEnvironmentStructureUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<StructureDto>) => r.body as StructureDto)
    );
  }

  /**
   * Path part for operation getEnvironmentStructureUsingGet
   */
  static readonly GetEnvironmentStructureUsingGetPath = '/api/systemdescription/structure/env';

  /**
   * getEnvironmentStructure.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getEnvironmentStructureUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEnvironmentStructureUsingGet$Response(params?: {

    /**
     * envKey
     */
    envKey?: string;
  }): Observable<StrictHttpResponse<StructureDto>> {

    const rb = new RequestBuilder(this.rootUrl, SystemDescriptionControllerService.GetEnvironmentStructureUsingGetPath, 'get');
    if (params) {
      rb.query('envKey', params.envKey, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<StructureDto>;
      })
    );
  }

  /**
   * getEnvironmentStructure.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getEnvironmentStructureUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEnvironmentStructureUsingGet(params?: {

    /**
     * envKey
     */
    envKey?: string;
  }): Observable<StructureDto> {

    return this.getEnvironmentStructureUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<StructureDto>) => r.body as StructureDto)
    );
  }

  /**
   * Path part for operation getSystemTypeDescriptorsUsingGet
   */
  static readonly GetSystemTypeDescriptorsUsingGetPath = '/api/systemdescription/types';

  /**
   * getSystemTypeDescriptors.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSystemTypeDescriptorsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSystemTypeDescriptorsUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<Array<SystemTypeDescriptorDto>>> {

    const rb = new RequestBuilder(this.rootUrl, SystemDescriptionControllerService.GetSystemTypeDescriptorsUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<SystemTypeDescriptorDto>>;
      })
    );
  }

  /**
   * getSystemTypeDescriptors.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getSystemTypeDescriptorsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSystemTypeDescriptorsUsingGet(params?: {
  }): Observable<Array<SystemTypeDescriptorDto>> {

    return this.getSystemTypeDescriptorsUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<SystemTypeDescriptorDto>>) => r.body as Array<SystemTypeDescriptorDto>)
    );
  }

}
