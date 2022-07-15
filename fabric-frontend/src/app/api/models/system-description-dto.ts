/* tslint:disable */
/* eslint-disable */
import { EnvironmentDto } from './environment-dto';
import { SystemComponentDto } from './system-component-dto';
export interface SystemDescriptionDto {
  environments?: Array<EnvironmentDto>;
  settings?: { [key: string]: {  } };
  systemComponents?: Array<SystemComponentDto>;
}
