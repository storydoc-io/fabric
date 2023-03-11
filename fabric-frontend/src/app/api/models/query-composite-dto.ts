/* tslint:disable */
/* eslint-disable */
import { QueryDto } from './query-dto';
import { ResultDto } from './result-dto';
export interface QueryCompositeDto {
  children?: Array<QueryCompositeDto>;
  id?: string;
  query?: QueryDto;
  result?: ResultDto;
}
