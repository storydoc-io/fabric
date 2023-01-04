import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ModalService} from "../../../common/modal/modal-service";
import {SystemDescriptionService, SystemDescriptionWrapper} from "../../system-description.service";
import {EnvironmentDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {MetaModelDialogData, MetaModelDialogSpec} from "./meta-model-dialog/meta-model-dialog.component";
import {MetaDataService} from "./meta-data.service";

@Component({
    selector: 'app-meta-model-panel',
    templateUrl: './meta-model-panel.component.html',
    styleUrls: ['./meta-model-panel.component.scss']
})
export class MetaModelPanelComponent extends HasConfirmationDialogMixin implements OnInit, OnChanges {

    constructor(modalService: ModalService, private service: SystemDescriptionService, private metaDataService: MetaDataService) {
        super(modalService);
    }

    @Input()
    systemDescription: SystemDescriptionDto

    @Input()
    systemComponent: SystemComponentDto

    ngOnInit(): void {
    }

    environment: EnvironmentDto

    metaModel$ = this.metaDataService.metaModel$

    ngOnChanges(changes: SimpleChanges): void {
        if (this.systemDescription && this.systemComponent) {
            this.environment = new SystemDescriptionWrapper(this.systemDescription).getDefaultEnvironment()
            this.metaDataService.loadMetaModel(this.environment.key, this.systemComponent.key)
        }
    }

    metaModelDialogSpec: MetaModelDialogSpec;

    confirmationDialogId(): string {
        return "metadata-confirmation-dialog-id";
    }

    metaModelDialogId() {
        return "metadata-dialog"
    }

    private openMetaModelDialog(spec: MetaModelDialogSpec) {
        this.metaModelDialogSpec = spec
        this.modalService.open(this.metaModelDialogId())
    }

    private closeMetaModelDialog() {
        this.metaModelDialogSpec = null
        this.modalService.close(this.metaModelDialogId())
    }

    addMetaModel(systemDescription: SystemDescriptionDto) {
        this.openMetaModelDialog({
            systemComponent: this.systemComponent,
            systemDescription,
            data: {
                environmentKey: null,
            },
            confirm: (data: MetaModelDialogData) => {
                this.closeMetaModelDialog()
                this.metaDataService.fetchMetaModel(this.systemComponent, data.environmentKey)
            },
            cancel: () => this.closeMetaModelDialog()
        })
    }

    spinnerClicked() {
        console.log('clicked!!')
    }
}
