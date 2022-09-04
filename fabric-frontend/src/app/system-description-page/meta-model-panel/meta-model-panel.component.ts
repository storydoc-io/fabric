import {Component, Input, OnInit} from '@angular/core';
import {ModalService} from "../../common/modal/modal-service";
import {SystemDescriptionService} from "../system-description.service";
import {StructureDto, SystemComponentDto, SystemDescriptionDto} from "@fabric/models";
import {HasConfirmationDialogMixin} from "@fabric/common";
import {MetaModelDialogData, MetaModelDialogSpec} from "./meta-model-dialog/meta-model-dialog.component";

@Component({
  selector: 'app-meta-model-panel',
  templateUrl: './meta-model-panel.component.html',
  styleUrls: ['./meta-model-panel.component.scss']
})
export class MetaModelPanelComponent extends HasConfirmationDialogMixin  implements OnInit {

  constructor(modalService: ModalService, private service: SystemDescriptionService) {
    super(modalService);
  }

  ngOnInit(): void {
  }

  @Input()
  systemDescription: SystemDescriptionDto

  @Input()
  systemComponent: SystemComponentDto

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
