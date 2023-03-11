/* tslint:disable */
/* eslint-disable */
import { NavItem } from './nav-item';
export interface QueryDto {
  attributes?: { [key: string]: string };
  environmentKey?: string;
  navItem?: NavItem;
  systemComponentKey?: string;
}
