import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { EMPTY } from '../../constants/app-const.constant';
import { FieldType } from '../../enums/field-type.enum';

@Component({
  selector: 'generic-input-field',
  templateUrl: './generic-input-field.component.html',
  styleUrls: ['./generic-input-field.component.scss'],
  providers: [
      {
        provide: NG_VALUE_ACCESSOR,
        useExisting: forwardRef(() => GenericInputFieldComponent),
        multi: true
      }
    ]
})
export class GenericInputFieldComponent implements ControlValueAccessor {
  @Input() public className: string;
  @Input() public disabled: boolean;
  @Input() public error: string;
  @Input() public helperText: string;
  @Input() public isReadOnly: boolean;
  @Input() public label: string;
  @Input() public name: string;
  @Input() public placeholder: string;
  @Input() public required: boolean;
  @Input() public type: string;

  public value: string;
  
  public constructor() {
      this.className = EMPTY;
      this.disabled = false;
      this.error = EMPTY;
      this.helperText = EMPTY;
      this.isReadOnly = false;
      this.label = EMPTY;
      this.name = EMPTY;
      this.placeholder = EMPTY;
      this.required = false;
      this.type = FieldType.Text;
      this.value = EMPTY;
  }

  public handleInput(event: Event): void {
    const target = event.target as HTMLTextAreaElement;
    const value = target?.value || EMPTY;
    this.value = value;
    this.onChange(value);
    this.onTouched();
  }

  public handleBlur(): void {
    this.onTouched();
  }
  
  public onChange = (value: string) => {};
  
  public onTouched = () => {};

  public registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  public registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  public setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  public writeValue(value: string | number | null): void {
    if (value === null || value === undefined) {
      this.value = EMPTY;
    } else {
      this.value = String(value);
    }
  }
}