import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";
import {FabricCommonModule} from "../common/common.module";

import {SystemComponentDialogComponent} from './data-sources-page/system-component-panel/system-component-dialog/system-component-dialog.component';
import {SystemComponentPanelComponent} from './data-sources-page/system-component-panel/system-component-panel.component';
import {DataSourcesPageComponent} from './data-sources-page/data-sources-page.component';
import {EnvironmentDialogComponent} from './environments-page/environment-panel/environment-dialog/environment-dialog.component';
import {EnvironmentPanelComponent} from './environments-page/environment-panel/environment-panel.component';
import {EnvironmentsPageComponent} from './environments-page/environments-page.component';
import {MetaModelPanelComponent} from './meta-model-page/meta-model-panel/meta-model-panel.component';
import {MetaModelDialogComponent} from './meta-model-page/meta-model-panel/meta-model-dialog/meta-model-dialog.component';
import {MongoMetamodelPanelComponent} from './meta-model-page/meta-model-panel/mongo-metamodel-panel/mongo-metamodel-panel.component';
import {MetaModelViewComponent} from './meta-model-page/meta-model-panel/meta-model-view/meta-model-view.component';
import {MetaModelPageComponent} from './meta-model-page/meta-model-page.component';
import {ConnectionSettingsDialogComponent} from './connection-settings-page/connection-settings-panel/connection-settings-dialog/connection-settings-dialog.component';
import {ConnectionSettingsPanelComponent} from './connection-settings-page/connection-settings-panel/connection-settings-panel.component';
import {ConnectionSettingsPageComponent} from './connection-settings-page/connection-settings-page.component';

@NgModule({
    declarations: [
        SystemComponentPanelComponent,
        SystemComponentDialogComponent,
        EnvironmentPanelComponent,
        EnvironmentDialogComponent,
        ConnectionSettingsPanelComponent,
        ConnectionSettingsDialogComponent,
        MetaModelPanelComponent,
        MetaModelDialogComponent,
        MongoMetamodelPanelComponent,
        MetaModelViewComponent,
        MetaModelPageComponent,
        ConnectionSettingsPageComponent,
        EnvironmentsPageComponent,
        DataSourcesPageComponent,
    ],
    imports: [
        CoreModule,
        FabricCommonModule
    ],
    exports: [

    ]
})
export class SystemDescriptionModule { }
