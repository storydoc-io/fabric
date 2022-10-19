import {NgModule} from "@angular/core";

import {CoreModule} from '../core.module';

import {DataSourceSelectionPanelComponent} from './data-source-selection-panel/data-source-selection-panel.component'

@NgModule({
    declarations: [
        DataSourceSelectionPanelComponent
    ],
    providers: [
    ],
    imports: [
        CoreModule,
    ],
    exports: [
        DataSourceSelectionPanelComponent
    ]
})
export class ComponentModule {

}
