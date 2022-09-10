/* tslint:disable */
/* eslint-disable */
export interface QueryResponseItemDto {
  attributes?: { [key: string]: string };
  children?: Array<QueryResponseItemDto>;
  queryResponseItemType?: string;
  systemType?: string;
}
