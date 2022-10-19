/* tslint:disable */
/* eslint-disable */
import { ConsoleResponseDescriptionDto } from './console-response-description-dto';
import { NavItem } from './nav-item';
import { TabularResponse } from './tabular-response';
export interface ConsoleResponseItemDto {
  consoleOutputType?: 'JSON' | 'MESSAGE' | 'STACKTRACE' | 'TABULAR';
  content?: string;
  description?: ConsoleResponseDescriptionDto;
  navItems?: Array<NavItem>;
  systemType?: string;
  tabular?: TabularResponse;
}
