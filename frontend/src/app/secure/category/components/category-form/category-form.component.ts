import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CategoryRequest} from '../../../../core/models/request/categories/category-request.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ButtonType} from '../../../../shared/enums/button-type.enum';
import {CATEGORY_ICON_MAP} from '../../../../shared/constants/category-icon.const';
import {CategoryIcon} from '../../../../shared/enums/category-icon.enum';
import {EMPTY} from '../../../../shared/constants/app-const.constant';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CategoryFormComponent implements OnInit {
  @Input() public formTitle: string;
  @Input() public isReadOnly: boolean;
  @Input() public submitLabel: string;

  @Output() public formSubmit: EventEmitter<CategoryRequest>;
  @Output() public formCancel: EventEmitter<void>;

  public form!: FormGroup;
  public iconOptions: Array<{key: CategoryIcon; class: string}>;

  public readonly BUTTON_TYPE = ButtonType;

  private _categoryData: CategoryRequest | null;

  @Input() set categoryData(value: CategoryRequest | null) {
    this._categoryData = value;
    if (value && this.form) {
      this.form.patchValue(value);
    }
  }

  constructor(private _fb: FormBuilder) {
    this.formTitle = 'Create Category';
    this.submitLabel = 'Save';
    this.isReadOnly = false;
    this.formSubmit = new EventEmitter<CategoryRequest>();
    this.formCancel = new EventEmitter<void>();
    this.iconOptions = Object.entries(CATEGORY_ICON_MAP).map(([key, value]) => ({
      key: key as CategoryIcon,
      class: value
    }));
    this._categoryData = null;
  }

  ngOnInit(): void {
    this._initialize();
  }

  public getFieldError(field: string): string {
    const control = this.form.get(field);
    if (control?.errors && (control.touched || control.dirty)) {
      if (control.errors['required']) return 'Required Field';
      if (control.errors['minlength']) return `Minimum ${control.errors['minlength'].requiredLength} characters`;
      if (control.errors['maxlength']) return `Maximum ${control.errors['maxlength'].requiredLength} characters`;
    }
    return EMPTY;
  }

  public onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) this.formSubmit.emit(this.form.value as CategoryRequest);
  }

  public onCancel(): void {
    this.formCancel.emit();
  }

  public closeModal(): void {
    this.formCancel.emit();
  }

  private _createForm(): FormGroup {
    return this._fb.group({
      id: [null],
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      icon: ['', [Validators.required]],
      description: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(300)]],
      active: [true]
    });
  }

  private _initialize(): void {
    this.form = this._createForm();
    if (this._categoryData) this.form.patchValue(this._categoryData);
  }
}