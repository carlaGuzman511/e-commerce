import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { EMPTY } from '../../constants/app-const.constant';

@Component({
  selector: 'generic-checkbox-field',
  templateUrl: './generic-checkbox-field.component.html',
  styleUrls: ['./generic-checkbox-field.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => GenericCheckboxFieldComponent),
      multi: true
    }
  ]
})
export class GenericCheckboxFieldComponent implements ControlValueAccessor {
  @Input() public checked: boolean;
  @Input() public className: string;
  @Input() public disabled: boolean;
  @Input() public error: string;
  @Input() public helperText: string;
  @Input() public label: string;
  @Input() public name: string;
  @Input() public required: boolean;

  public value: boolean;

  public constructor(){
    this.checked = false;
    this.className = EMPTY;
    this.disabled = false;
    this.error = EMPTY;
    this.helperText = EMPTY;
    this.label = EMPTY;
    this.name = EMPTY;
    this.required = false;
    this.value = false;
  }

  public handleInput(event: Event): void {
    const target = event.target as HTMLInputElement;
    const value = target.checked; 
    this.value = value;
    this.onChange(value);
    this.onTouched();
  }

  public handleBlur(): void {
    this.onTouched();
  }

  public onChange: (value: boolean) => void = () => {};
  
  public onTouched: () => void = () => {};

  public registerOnChange(fn: (value: boolean) => void): void {
    this.onChange = fn;
  }

  public registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  public setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  public writeValue(value: boolean): void {
    this.value = value || false;
  }
}