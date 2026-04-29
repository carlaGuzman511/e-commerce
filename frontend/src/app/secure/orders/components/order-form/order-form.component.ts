import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl, FormArray } from '@angular/forms';
import { OrderStatus } from 'src/app/core/enums/order-status.enum';
import { Order } from 'src/app/core/models/order.model';
import { ButtonType } from 'src/app/shared/enums/button-type.enum';
import { SelectOption } from 'src/app/shared/interfaces/select-option';
import { enumToSelectOptions } from 'src/app/core/utils/enum-utils';
import { PaymentMethod } from 'src/app/core/enums/payment-method.enum';
import { DeliveryMethod } from 'src/app/core/enums/delivery-method.enum';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { OrderItem } from 'src/app/core/models/response/order-item.model';
@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class OrderFormComponent implements OnInit {
  public readonly ButtonType = ButtonType;

  public deliveryMethodOptions: SelectOption[];
  public form: FormGroup;
  public orderStatusOptions: SelectOption[];
  public paymentMethodOptions: SelectOption[];

  private _orderData: Order | null;

  @Input() public isReadOnly: boolean;
  @Input() public formTitle: string;
  @Input() public submitLabel: string;

  @Input() set orderData(value: Order | null) {
    this._orderData = value;
    if (value && this.form) {
      this.form.patchValue(value);
    }
  }

  @Output() public formSubmit: EventEmitter<Order>;
  @Output() public formCancel: EventEmitter<void>;

  constructor(private _fb: FormBuilder) {
    this.form = this.createForm();
    this.formTitle = 'Create Order';
    this.submitLabel = 'Save';
    this._orderData = null;
    this.isReadOnly = false;
    this.orderStatusOptions = enumToSelectOptions(OrderStatus);
    this.paymentMethodOptions = enumToSelectOptions(PaymentMethod);
    this.deliveryMethodOptions = enumToSelectOptions(DeliveryMethod);
    this.formSubmit = new EventEmitter<Order>();
    this.formCancel = new EventEmitter<void>();

  }
  
  get orderData(): Order | null {
    return this._orderData;
  }

  ngOnInit(): void {
    this._initialize();
  }

  public createForm(): FormGroup {
    return this._fb.group({
      id: [this._orderData?.id || null],
      userId: [this._orderData?.userId || null],
      branchId: [this._orderData?.branchId || null],
      orderStatus: [OrderStatus.Pending, Validators.required],
      paymentMethod: [PaymentMethod.Card, Validators.required],
      deliveryMethod: [DeliveryMethod.StorePickup, Validators.required],
      total: [this._orderData?.total || 0, Validators.required],
      discounts: [this._orderData?.discounts || 0, Validators.required],
      items: this._fb.array(this._orderData?.items?.map(item => this._createItemGroup(item)) || [this._createItemGroup()]),
      address: this._fb.group({
        street: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        city: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        postalCode: [EMPTY],
        country: [EMPTY, [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
        state: [EMPTY]
      })
    });
  }

  get items(): FormArray {
    return this.form.get('items') as FormArray;
  }

  public addItem(): void {
    this.items.push(this._createItemGroup());
  }

  public removeItem(index: number): void {
    this.items.removeAt(index);
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
      this.formSubmit.emit(this.form.value as Order);
    } 
  }

  get invalidFields(): string {
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

  private _createItemGroup(item?: OrderItem): FormGroup {
    return this._fb.group({
      productId: [item?.productId || 0, Validators.required],
      variantId: [item?.variantId || 0, Validators.required],
      quantity: [item?.quantity || 0, Validators.required],
      price: [item?.price || 0, Validators.required],
      discount: [item?.discount || 0, Validators.required],
      total: [item?.total || 0, Validators.required],
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
    if (this._orderData) {
      this.form.patchValue(this._orderData);
    }
  }
}