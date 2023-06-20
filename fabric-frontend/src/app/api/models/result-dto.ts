/* tslint:disable */
/* eslint-disable */
import { DocumentsResultSet } from './documents-result-set';
import { NavItem } from './nav-item';
import { TabularResultSet } from './tabular-result-set';
import { TabularResultSetMetaDataDto } from './tabular-result-set-meta-data-dto';
export interface ResultDto {
  content?: string;
  description?: TabularResultSetMetaDataDto;
  documentsResultSet?: DocumentsResultSet;
  navItems?: Array<NavItem>;
  resultType?: 'JSON' | 'MESSAGE' | 'STACKTRACE' | 'TABULAR';
  systemType?: string;
  tabular?: TabularResultSet;
}
