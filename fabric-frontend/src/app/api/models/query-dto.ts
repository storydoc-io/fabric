/* tslint:disable */
/* eslint-disable */
import { NavItem } from './nav-item';
import { PagingDto } from './paging-dto';
export interface QueryDto {
  attributes?: { [key: string]: string };
  environmentKey?: string;
  navItem?: NavItem;
  paging?: PagingDto;
  systemComponentKey?: string;
}
