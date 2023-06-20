/* tslint:disable */
/* eslint-disable */
import { Column } from './column';
import { PagingDto } from './paging-dto';
import { Row } from './row';
export interface TabularResultSet {
  columns?: Array<Column>;
  pagingInfo?: PagingDto;
  pkColumns?: Array<Column>;
  rows?: Array<Row>;
  tableName?: string;
}
