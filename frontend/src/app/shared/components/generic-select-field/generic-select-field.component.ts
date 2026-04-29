import { Component, forwardRef, Input } from '@angular/core';
import { EMPTY } from '../../constants/app-const.constant';
import { SelectOption } from 'src/app/shared/interfaces/select-option';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'generic-select-field',
  templateUrl: './generic-select-field.component.html',
  styleUrls: ['./generic-select-field.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => GenericSelectFieldComponent),
      multi: true
    }
  ]
})
export class GenericSelectFieldComponent implements ControlValueAccessor {
  @Input() public className: string;
  @Input() public disabled: boolean;
  @Input() public error: string;
  @Input() public helperText: string;
  @Input() public label: string;
  @Input() public name: string;
  @Input() public options: SelectOption[];
  @Input() public placeholder: string;
  @Input() public required: boolean;

  public value: string | number | null;

  constructor() {
    this.className = EMPTY;
    this.disabled = false;
    this.error = EMPTY;
    this.helperText = EMPTY;
    this.label = EMPTY;
    this.name = EMPTY;
    this.options = [];
    this.placeholder = 'Select an option';
    this.required = false;
    this.value = EMPTY;
  }

  public handleInput(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const value = target.value;
    this.value = value;
    this.onChange(value);
    this.onTouched();
  }

  public handleBlur(): void {
    this.onTouched();
  }

  public onChange = (value: string | number | null) => {};

  public onTouched = () => {};

  public registerOnChange(fn: (value: string | number | null) => void): void {
    this.onChange = fn;
  }

  public registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  public setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  public writeValue(value: string | null): void {
    const exists = this.options.some(option => option.value === value);
    this.value = exists ? value! : EMPTY;
  }

  public trackByOptionValue(index: number, option: { value: string | number }): string | number {
    return option.value;
  }
}
