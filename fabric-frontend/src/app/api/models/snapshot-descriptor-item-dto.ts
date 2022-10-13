/* tslint:disable */
/* eslint-disable */
export interface SnapshotDescriptorItemDto {
  attributes?: { [key: string]: string };
  children?: Array<SnapshotDescriptorItemDto>;
  id?: string;
  itemType?: string;
  systemType?: string;
}
