import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";

import { MongoNavmodelPanelComponent } from './mongo-navmodel-panel/mongo-navmodel-panel.component';
import { EnvironmentDialogComponent } from './environment-panel/environment-dialog/environment-dialog.component';
import { SystemComponentDialogComponent } from './system-component-panel/system-component-dialog/system-component-dialog.component';
import { SettingsDialogComponent } from './settings-panel/settings-dialog/settings-dialog.component';
import { SystemDescriptionPageComponent } from './system-description-page.component';
import { EnvironmentPanelComponent } from './environment-panel/environment-panel.component';
import { SystemComponentPanelComponent } from './system-component-panel/system-component-panel.component';
import { SettingsPanelComponent } from './settings-panel/settings-panel.component';
import { MetaModelPanelComponent } from './meta-model-panel/meta-model-panel.component';
import { MetaModelDialogComponent } from './meta-model-panel/meta-model-dialog/meta-model-dialog.component';
import { MongoMetamodelPanelComponent } from './meta-model-panel/mongo-metamodel-panel/mongo-metamodel-panel.component';

@NgModule({
    declarations: [
        SystemDescriptionPageComponent,
        SystemComponentPanelComponent,
        SystemComponentDialogComponent,
        EnvironmentPanelComponent,
        EnvironmentDialogComponent,
        SettingsPanelComponent,
        SettingsDialogComponent,
        MongoNavmodelPanelComponent,
        MetaModelPanelComponent,
        MetaModelDialogComponent,
        MongoMetamodelPanelComponent,
    ],
    imports: [
        CoreModule,
        FabricCommonModule
    ],
    exports: [
        SystemDescriptionPageComponent,
    ]
})
export class SystemDescriptionModule { }
