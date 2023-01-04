/* tslint:disable */
/* eslint-disable */
import { AttributeDto } from './attribute-dto';
export interface EntityDto {
  attributes?: Array<AttributeDto>;
  entityType?: string;
  json?: boolean;
  name?: string;
}
