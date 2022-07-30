/* tslint:disable */
/* eslint-disable */
import { SnapshotComponentDto } from './snapshot-component-dto';
import { SnapshotId } from './snapshot-id';
export interface SnapshotDto {
  componentSnapshots?: Array<SnapshotComponentDto>;
  environmentKey?: string;
  name?: string;
  snapshotId?: SnapshotId;
}
