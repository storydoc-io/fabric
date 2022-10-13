/* tslint:disable */
/* eslint-disable */
import { SnapshotDescriptorItemDto } from './snapshot-descriptor-item-dto';
export interface SnapshotDescriptorDto {
  componentDescriptors?: Array<SnapshotDescriptorItemDto>;
  environmentKey?: string;
  name?: string;
}
