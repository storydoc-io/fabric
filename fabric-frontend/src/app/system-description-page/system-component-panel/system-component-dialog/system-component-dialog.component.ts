import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {showValidationMessages, unique} from '@fabric/common'
import {SystemDescriptionService} from '../../system-description.service';

export interface SystemComponentDialogData {
    key: string,
    label: string,
    systemType: string,
}

export interface SystemComponentDialogSpec {
    keys: string[];
    data: SystemComponentDialogData
    cancel: () => void
    confirm: (data: SystemComponentDialogData) => void
}


@Component({
    selector: 'app-system-component-dialog',
    templateUrl: './system-component-dialog.component.html',
    styleUrls: ['./system-component-dialog.component.scss']
})
export class SystemComponentDialogComponent implements OnInit, OnChanges {

    constructor(private service: SystemDescriptionService) {
        this.systemTypes = this.service.getSystemTypes()
    }

    ngOnInit(): void {
    }

    @Input()
    spec: SystemComponentDialogSpec

    systemTypes: string[]

    ngOnChanges(changes: SimpleChanges): void {
        if (this.spec != null) {
            this.keyControl.setValidators([Validators.required, unique('key', this.spec.keys)])
            this.formGroup.setValue(this.spec.data)
            this.formGroup.markAsPristine()
            this.formGroup.markAsUntouched()
        }
    }

    formGroup: FormGroup = new FormGroup({
        key: new FormControl(null, [Validators.required]),
        label: new FormControl(null, [Validators.required]),
        systemType: new FormControl(null, [Validators.required]),
    })

    public get keyControl(): FormControl {
        return <FormControl> this.formGroup.get('key')
    }

    public keyControlInvalid() : boolean {
        return showValidationMessages(this.keyControl)
    }

    public get labelControl(): FormControl {
        return <FormControl> this.formGroup.get('label')
    }

    labelControlInvalid() {
        return showValidationMessages(this.labelControl)
    }

    private get systemTypeControl(): FormControl {
        return <FormControl> this.formGroup.get('systemType')
    }


    systemTypeControlInvalid() {
        return showValidationMessages(this.systemTypeControl)
    }

    cancel() {
        this.spec.cancel()
    }

    confirm() {
        this.spec.confirm(this.formGroup.value)
    }


}
