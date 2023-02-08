import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SystemComponentDto, SystemDescriptionDto, SystemTypeDescriptorDto} from "@fabric/models";
import {SystemComponentDialogData, SystemComponentDialogSpec} from "./system-component-dialog/system-component-dialog.component";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {ModalService} from "../../../common/modal/modal-service";
import {SystemDescriptionService} from "../../system-description.service";
import {RoutingService} from "../../../common/routing.service";

@Component({
    selector: 'app-system-component-panel',
    templateUrl: './system-component-panel.component.html',
    styleUrls: ['./system-component-panel.component.scss']
})
export class SystemComponentPanelComponent extends HasConfirmationDialogMixin implements OnInit {

    constructor(modalService: ModalService, private service: SystemDescriptionService, private routingService : RoutingService) {
        super(modalService);
    }

    ngOnInit(): void {
    }



    @Input()
    systemTypes: SystemTypeDescriptorDto[]

    @Input()
    systemDescription: SystemDescriptionDto

    @Input()
    selection: SystemComponentDto

    @Output()
    selectionChanged = new EventEmitter<SystemComponentDto>()

    select(systemComponent: SystemComponentDto) {
        this.selectionChanged.emit(systemComponent)
    }

    // data sources

    systemComponentDialogSpec: SystemComponentDialogSpec

    systemComponentDialogId(): string {
        return 'system-component-dialog'
    }

    private openSystemComponentDialog(spec: SystemComponentDialogSpec) {
        this.systemComponentDialogSpec = spec
        this.modalService.open(this.systemComponentDialogId())
    }

    private closeSystemComponentDialog() {
        this.systemComponentDialogSpec = null
        this.modalService.close(this.systemComponentDialogId())
    }

    addComponent(systemDescription: SystemDescriptionDto) {
        this.openSystemComponentDialog({
            systemTypes: this.systemTypes,
            keys: this.systemComponentKeys(systemDescription),
            data: {
                key: null,
                label: null,
                systemType: null
            },
            confirm: data => {
                let systemComponentDto = <SystemComponentDto>data
                this.closeSystemComponentDialog()
                this.service.addSystemComponent(systemComponentDto)
                this.selectionChanged.emit(systemComponentDto)
            },
            cancel: () => this.closeSystemComponentDialog()
        })
    }

    editComponent(systemDescription: SystemDescriptionDto, systemComponent: SystemComponentDto) {
        this.openSystemComponentDialog({
            systemTypes: this.systemTypes,
            keys: this.systemComponentKeys(systemDescription, systemComponent),
            data: <SystemComponentDialogData>{...systemComponent},
            confirm: data => {
                this.closeSystemComponentDialog()
                this.service.updateSystemComponent(systemComponent, <SystemComponentDto>data)
            },
            cancel: () => this.closeSystemComponentDialog()
        })
    }


    removeComponent(systemComponent: SystemComponentDto) {
        this.openConfirmationDialog({
            title: 'Confirm delete',
            message: `Delete datasource ${systemComponent.key}?`,
            warning: `This will also remove ${systemComponent.key}'s connection settings`,
            confirm: () => {
                this.closeConfirmationDialog()
                this.service.deleteSystemComponent(systemComponent)
                this.selectionChanged.emit(null)
            },
            cancel: () => this.closeConfirmationDialog()
        })
    }

    metaModel(systemComponent: SystemComponentDto) {
        this.routingService.navigateToMetaModelPage(systemComponent?.key)
    }

    settings(systemComponent: SystemComponentDto) {
        this.routingService.navigateToConnectionSettingsPage(systemComponent?.key)
    }


    private systemComponentKeys(systemDescription: SystemDescriptionDto, exceptSystemComponent?: SystemComponentDto): string[] {
        return systemDescription.systemComponents
            .filter(systemComponent => systemComponent.key != exceptSystemComponent?.key)
            .map(systemComponent => systemComponent.key);
    }

    confirmationDialogId(): string {
        return 'confirmation-dialog-systemcomponent'
    }


}
