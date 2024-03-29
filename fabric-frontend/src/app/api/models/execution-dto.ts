/* tslint:disable */
/* eslint-disable */
export interface ExecutionDto {
  children?: Array<ExecutionDto>;
  label?: string;
  percentDone?: number;
  status?: 'DONE' | 'ERROR' | 'PAUSED' | 'READY' | 'RUNNING' | 'UNRESPONSIVE';
}
