/* tslint:disable */
/* eslint-disable */
import { NavItem } from './nav-item';
import { TabularResultSet } from './tabular-result-set';
import { TabularResultSetMetaDataDto } from './tabular-result-set-meta-data-dto';
export interface ResultDto {
  content?: string;
  description?: TabularResultSetMetaDataDto;
  navItems?: Array<NavItem>;
  resultType?: 'JSON' | 'MESSAGE' | 'STACKTRACE' | 'TABULAR';
  systemType?: string;
  tabular?: TabularResultSet;
}
