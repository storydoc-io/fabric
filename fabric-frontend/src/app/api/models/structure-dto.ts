/* tslint:disable */
/* eslint-disable */
export interface StructureDto {
  attributes?: { [key: string]: string };
  children?: Array<StructureDto>;
  id?: string;
  structureType?: string;
  systemType?: string;
}
