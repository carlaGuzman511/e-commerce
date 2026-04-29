import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { EMPTY } from '../../constants/app-const.constant';

@Component({
  selector: 'generic-text-area-field',
  templateUrl: './generic-text-area-field.component.html',
  styleUrls: ['./generic-text-area-field.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => GenericTextAreaFieldComponent),
      multi: true
    }
  ]
})
export class GenericTextAreaFieldComponent implements ControlValueAccessor {
  @Input() public className: string;
  @Input() public disabled: boolean;
  @Input() public error: string;
  @Input() public helperText: string;
  @Input() public isReadOnly: boolean;
  @Input() public label: string;
  @Input() public name: string;
  @Input() public placeholder: string;
  @Input() public required: boolean;
  @Input() public rows: number;
  
  public value: string;
  
  public constructor(){
    this.className = EMPTY;
    this.disabled = false;
    this.error = EMPTY;
    this.helperText = EMPTY;
    this.isReadOnly = false;
    this.label = EMPTY;
    this.name = EMPTY;
    this.placeholder = EMPTY;
    this.required = false;
    this.rows = 4;
    this.value = EMPTY;
  }

  public handleInput(event: Event): void {
    const target = event.target as HTMLTextAreaElement;
    const value = target?.value || '';
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

  public writeValue(value: string): void {
    this.value = value || EMPTY;
  }
}