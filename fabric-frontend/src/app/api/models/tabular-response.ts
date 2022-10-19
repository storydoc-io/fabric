/* tslint:disable */
/* eslint-disable */
import { Column } from './column';
import { Row } from './row';
export interface TabularResponse {
  columns?: Array<Column>;
  pkColumns?: Array<Column>;
  rows?: Array<Row>;
  tableName?: string;
}
