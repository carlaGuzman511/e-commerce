import { Component, OnInit, Input, Output, EventEmitter, OnDestroy, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Branch } from 'src/app/core/models/branch.model';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { ButtonType } from 'src/app/shared/enums/button-type.enum';

@Component({
  selector: 'app-branch-form',
  templateUrl: './branch-form.component.html',
  styleUrls: ['./branch-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class BranchFormComponent implements OnInit {
  @Input() public formTitle: string;
  @Input() public submitLabel: string;
  @Input() public isReadOnly: boolean;

  @Output() public formSubmit: EventEmitter<Branch>;
  @Output() public formCancel: EventEmitter<void>;

  public readonly buttonType = ButtonType;
  public form: FormGroup;

  private _branchData: Branch | null;
  
  @Input() set branchData(value: Branch | null) {
    this._branchData = value;
    if (value && this.form) {
      this.form.patchValue(value);
    }
  }

  constructor(private _fb: FormBuilder) {
    this.form = this.createForm();
    this.formTitle = 'Create Branch';
    this.submitLabel = 'Save';
    this._branchData = null;
    this.isReadOnly = false;
    this.formSubmit = new EventEmitter<Branch>();
    this.formCancel = new EventEmitter<void>();
  }
  
  ngOnInit(): void {
    this._initialize();
  }

  public createForm(): FormGroup {
    return this._fb.group({
      id: [this._branchData?.id || null],
      name: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      managerName: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      managerPhone: [EMPTY, [Validators.required]],
      openingHours: [EMPTY, [Validators.required]],
      closingHours: [EMPTY, [Validators.required]],
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
      this.formSubmit.emit(this.form.value as Branch);
    }
  }

  public get branchData(): Branch | null {
    return this._branchData;
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
    if (this._branchData) {
      this.form.patchValue(this._branchData);
    }
  }
}