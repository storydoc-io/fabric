/* tslint:disable */
/* eslint-disable */
import { Column } from './column';
import { ConsoleResponseDescriptionDto } from './console-response-description-dto';
import { Row } from './row';
export interface ConsoleResponseItemDto {
  consoleOutputType?: 'JSON' | 'MESSAGE' | 'STACKTRACE' | 'TABULAR';
  content?: string;
  description?: ConsoleResponseDescriptionDto;
  systemType?: string;
  tabularData?: Array<Row>;
  tabularDataDescription?: Array<Column>;
}
