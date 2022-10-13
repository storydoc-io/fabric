/* tslint:disable */
/* eslint-disable */
export interface ConsoleResponseDescriptionDto {
  attributes?: { [key: string]: string };
  children?: Array<ConsoleResponseDescriptionDto>;
  responseDescriptionType?: string;
  systemType?: string;
}
