import {Component, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {DatePipe} from '@angular/common';
import {SystemDescriptionService} from "../../system-description-page/system-description.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {StructureDto} from "@fabric/models";
import {ITreeModel, ITreeNode, ITreeOptions} from "@circlon/angular-tree-component/lib/defs/api";

export interface SnapshotDialogData {
    environment: string,
    name: string,
}

export interface SnapshotDialogSpec {
    data: SnapshotDialogData
    cancel: () => void
    confirm: (data: SnapshotDialogData) => void
    useNameGenerator: boolean
}

export interface SnapshotDialogTreeNode {
    name: string,
    structureDto: StructureDto,
    children: SnapshotDialogTreeNode[],
    data? : any
    parent?: SnapshotDialogTreeNode
}

export class StructureDto2TreeNodeConverter {

    public run(structureDto: StructureDto): SnapshotDialogTreeNode[] {
        if (!structureDto) return []
        let node = this.runRecursive(structureDto)
        return [node]
    }

    runRecursive(structureDto: StructureDto): SnapshotDialogTreeNode {
        if (!structureDto) return null
        let node = <SnapshotDialogTreeNode> {
            structureDto,
            name: structureDto.id ? structureDto.id : '',
        }
        node.children = structureDto.children?.map(child => {
            return this.runRecursive(child)
        })
        return node
    }


}



@Component({
    selector: 'app-snapshot-dialog',
    templateUrl: './snapshot-dialog.component.html',
    styleUrls: ['./snapshot-dialog.component.scss']
})
export class SnapshotDialogComponent implements OnInit {

    constructor(private systemDescriptionService: SystemDescriptionService) {
    }

    systemDescription$ = this.systemDescriptionService.systemDescription$

    ngOnInit(): void {
    }

    @Input()
    spec: SnapshotDialogSpec

    ngOnChanges(changes: SimpleChanges): void {
        if (this.spec != null) {
            this.formGroup.setValue(this.spec.data)
            this.treeNodes = []
        }
    }

    formGroup: FormGroup = new FormGroup({
        environment : new FormControl(null, [Validators.required]),
        name : new FormControl(null, [Validators.required]),
    })

    private get environmentControl(): FormControl {
        return <FormControl> this.formGroup.get('environment')
    }

    private get nameControl(): FormControl {
        return <FormControl> this.formGroup.get('name')
    }

    // tree

    treeNodes: SnapshotDialogTreeNode[]  = []

    options: ITreeOptions = {
        useCheckbox: true,
    }

    @ViewChild('tree') tree;

    onSelect(event) {
        try {
            console.log('select: ', event.node);
        } catch (e) {
            console.log(e.message)
        }
    }

    ondeSelect(event) {
        try {
            console.log('deselect: ', event.node);
        } catch (e) {
            console.log(e.message)
        }
    }


    test() {
        function logRecursive(model: ITreeModel, node: ITreeNode) {
            if (node) {
                console.log(node.data)
                // @ts-ignore'
                console.log('selected: ', node.isSelected)
                node.children?.forEach(child => logRecursive(model, child))
            }
        }

        console.log('selected: ', logRecursive(this.tree.treeModel, this.tree.treeModel.roots[0]))
    }


    datepipe: DatePipe = new DatePipe('en-US')

    onEnvironmentChanged() {
        let env = this.environmentControl.value
        if (this.spec.useNameGenerator) {
            let timeStamp = this.datepipe.transform(new Date(), 'YYYY-MM-dd HH:mm:ss')
            this.nameControl.setValue(`${env} snapshot ${timeStamp}`)
        }
        this.systemDescriptionService.loadEnvironmentSystemStructure(env).then((structureDto) => {
            this.treeNodes = new StructureDto2TreeNodeConverter().run(structureDto)
        })
    }

    onNameChangedManually() {
        this.spec.useNameGenerator = false
    }

    cancel() {
        this.spec.cancel()
    }

    confirm() {
        this.test()
        this.spec.confirm(this.formGroup.value)
    }

}
