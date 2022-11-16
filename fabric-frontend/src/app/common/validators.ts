import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export const unique = function (fieldName: string, values: string[]): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        let controlValue: string = control.value;
        const invalid = controlValue && !!values.find(val => val && controlValue && val.toUpperCase() === controlValue.toUpperCase())
        return invalid ? {unique: { value: `The ${fieldName} '${controlValue}' is already in use.`}} : null;
    }
}

export const showValidationMessages = function(control: AbstractControl): boolean {
    return (control.touched || control.dirty) && !control.valid
}
