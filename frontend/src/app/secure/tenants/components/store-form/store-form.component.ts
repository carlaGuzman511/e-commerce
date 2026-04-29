import { Component, OnInit, Input, Output, EventEmitter, OnDestroy, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { StoreRequest } from 'src/app/core/models/request/tenant/store-request.interface';
import { ButtonType } from 'src/app/shared/enums/button-type.enum';
import { CURRENCIES, DEFAULT_LANGUAGES, TIMEZONES } from 'src/app/secure/tenants/constants/store-form.contants';
import { SelectOption } from 'src/app/shared/interfaces/select-option';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';

@Component({
  selector: 'app-store-form',
  templateUrl: './store-form.component.html',
  styleUrls: ['./store-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class StoreFormComponent implements OnInit, OnDestroy {
  @Input() public formTitle: string;
  @Input() public submitLabel: string;
  @Input() public isReadOnly: boolean = false;

  @Output() public formSubmit = new EventEmitter<StoreRequest>();
  @Output() public formCancel = new EventEmitter<void>();

  public buttonType = ButtonType;
  public currencies: SelectOption[];
  public defaultLanguages: SelectOption[];
  public timezones: SelectOption[];
  public form: FormGroup;

  private _storeData: StoreRequest | null;
  
  @Input() set storeData(value: StoreRequest | null) {
    this._storeData = value;
    if (value && this.form) {
      this.form.patchValue(value);
    }
  }

  constructor(private _fb: FormBuilder) {
    this.form = this.createForm();
    this.formTitle = 'Create Store';
    this.submitLabel = 'Save';
    this._storeData = null;
    this.isReadOnly = false;
    this.currencies = CURRENCIES;
    this.defaultLanguages = DEFAULT_LANGUAGES;
    this.timezones = TIMEZONES;
  }
  
  public ngOnInit(): void {
    this._initialize();
  }

  public createForm(): FormGroup {
    return this._fb.group({
      id: [this._storeData?.id || null],
      name: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      info: [EMPTY, [Validators.required, Validators.minLength(10), Validators.maxLength(500)]],
      ownerName: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      ownerInfo: [EMPTY, [Validators.required, Validators.minLength(10), Validators.maxLength(500)]],
      currency: [EMPTY, Validators.required],
      defaultLanguage: [EMPTY, Validators.required],
      timezone: [EMPTY, Validators.required],
      address: this._fb.group({
        street: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        city: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        postalCode: [EMPTY],
        country: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        state: [EMPTY],
      })
    });
  }

  public getFieldError(fieldName: string): string {
    const field = this._getField(fieldName);
    if (field?.errors && (field.touched || field.dirty)) {
      if (field.errors['required']) return 'Required Field';
      if (field.errors['minlength']) return `Minimum ${field.errors['minlength'].requiredLength} characters`;
      if (field.errors['maxlength']) return `Maximum ${field.errors['maxlength'].requiredLength} characters`;
      if (field.errors['min']) return `Minimum value is ${field.errors['min'].min}`;
      if (field.errors['pattern']) return 'Invalid Format';
    }
    return EMPTY;
  }

  public onSubmit(): void {
    this._markAllFieldsAsTouched();
    
    if (this.form.valid) {
      this.formSubmit.emit(this.form.value as StoreRequest);
    }
  }

  public get storeData(): StoreRequest | null {
    return this._storeData;
  }

  public get invalidFields(): string {
    const invalidKeys = Object.keys(this.form.controls)
      .filter(key => this.form.get(key)?.errors);
      
    return invalidKeys.join(', ');
  }

  public onCancel(): void {
    this.formCancel.emit();
    this.form.reset({
      inStock: true,
      isActive: true,
      stockQuantity: 0
    });
  }

  public ngOnDestroy(): void { }

  private _markAllFieldsAsTouched(): void {
    Object.keys(this.form.controls).forEach(key => {
      const control = this.form.get(key);
      if (control instanceof FormGroup) {
        Object.keys(control.controls).forEach(nestedKey => {
          control.get(nestedKey)?.markAsTouched();
        });
      } else {
        control?.markAsTouched();
      }
    });
  }

  private _getField(fieldName: string): AbstractControl | null {
    if (fieldName.includes('.')) {
      const [parent, child] = fieldName.split('.');
      const parentGroup = this.form.get(parent);
      
      return parentGroup ? parentGroup.get(child) : null;
    }
    return this.form.get(fieldName);
  }

  private _initialize(): void {
    if (this._storeData) {
      this.form.patchValue(this._storeData);
    }
  }
}