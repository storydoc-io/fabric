import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ModalService} from "../../common/modal/modal-service";
import {SystemDescriptionService, SystemDescriptionWrapper} from "../system-description.service";
import {EnvironmentDto, StructureDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {MetaModelDialogData, MetaModelDialogSpec} from "./meta-model-dialog/meta-model-dialog.component";

@Component({
  selector: 'app-meta-model-panel',
  templateUrl: './meta-model-panel.component.html',
  styleUrls: ['./meta-model-panel.component.scss']
})
export class MetaModelPanelComponent extends HasConfirmationDialogMixin  implements OnInit, OnChanges {

  constructor(modalService: ModalService, private service: SystemDescriptionService) {
    super(modalService);
  }

  @Input()
  systemDescription: SystemDescriptionDto

  @Input()
  systemComponent: SystemComponentDto

  ngOnInit(): void {
  }

  environment: EnvironmentDto

  ngOnChanges(changes: SimpleChanges): void {
    if (this.systemDescription && this.systemComponent) {
        this.environment = new SystemDescriptionWrapper(this.systemDescription).getDefaultEnvironment()
        this.service.loadEnvironmentSystemComponentStructure(this.environment.key, this.systemComponent.key).then(dto => this.structureDTo = dto)
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

  structureDTo: StructureDto

  addMetaModel(systemDescription: SystemDescriptionDto) {
    this.openMetaModelDialog({
      systemComponent: this.systemComponent,
      systemDescription,
      data: {
        environmentKey: null,
      },
      confirm: (data: MetaModelDialogData) => {
        this.closeMetaModelDialog()
        this.service.fetchMetaModel(this.systemComponent, data.environmentKey).then((structureDto =>  this.structureDTo = structureDto))
      },
      cancel: () => this.closeMetaModelDialog()
    })
  }

}
