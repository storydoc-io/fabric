import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
    selector: 'app-validation-messages',
    templateUrl: './validation-messages.component.html',
    styleUrls: ['./validation-messages.component.scss']
})
export class ValidationMessagesComponent implements OnInit {

    constructor() {
    }

    ngOnInit(): void {
    }

    @Input()
    control: FormControl

    show(): boolean {
        return this.control.errors && (this.control.dirty || this.control.touched)
    }

    validationMessages(): string[] {
        let errors = this.control.errors
        if (errors) {
            return Object.keys(errors).map(key => errors[key].value)
        }
    }

}
