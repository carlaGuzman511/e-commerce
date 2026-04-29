import {ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Output} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {CategoryFacade} from '../../../../core/state-management/facades/category/category.facade';
import {Observable} from 'rxjs';
import {SelectOption} from '../../../../shared/interfaces/select-option';
import {map, take} from 'rxjs/operators';
import {ButtonType} from '../../../../shared/enums/button-type.enum';
import {getFormProduct, getFormVariants} from '../../constants/products-forms.utility';
import {CategoryHttpClientService} from '../../../../core/services/http/category-http-client.service';
import {ProductFacade} from '../../../../core/state-management/facades/product/product.facade';
import {MediaHttpClientService} from '../../../../core/services/http/media-http-client.service';
import {MediaRequest} from '../../../../core/models/request/media/media-request.model';
import {OwnerType} from '../../../../core/enums/owner-type.enum';
import {EMPTY} from '../../../../shared/constants/app-const.constant';

@Component({
  selector: 'app-modal-product-create',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductFormComponent {
  @Output() public closeEvent: EventEmitter<void>;

  public branchOption: SelectOption[];
  public categoriesOption$!: Observable<SelectOption[]>;
  public currentVariantIndex: number;
  public form: FormGroup;
  public previewImages: string;

  public readonly BUTTON_TYPE = ButtonType;
  public readonly TITLE: string = 'Form Product';

  constructor(
    private _productHttpClientService: CategoryHttpClientService,
    private _mediaHttpClientService: MediaHttpClientService,
    private _categoryFacade: CategoryFacade,
    private _productFacade: ProductFacade,
    private _cdr: ChangeDetectorRef,
    private _fb: FormBuilder
  ) {
    this.closeEvent = new EventEmitter<void>();
    this.form = getFormProduct(this._fb);
    this.addVariant();
    this.previewImages = EMPTY;
    this.branchOption = [
      {
        value: 1,
        label: 'sucursal 1'
      }
    ];
    this.currentVariantIndex = 0;
    this.categoriesOption$ = this._categoryFacade.selectCategories().pipe(
      map(categories =>
        categories.map(category => ({
          value: category.id,
          label: category.name
        }))
      )
    );
  }

  public get variants(): FormArray {
    return this.form.get('variants') as FormArray;
  }

  public getImages(variantIndex: number): FormArray {
    return this.variants.at(variantIndex).get('images') as FormArray;
  }

  public addVariant() {
    this.variants.push(getFormVariants(this._fb));
  }

  public getBranchStocks(index: number): FormArray {
    return this.variants.at(index).get('branchStocks') as FormArray;
  }

  public addBranchStock(index: number): void {
    this.getBranchStocks(index).push(
      this._fb.group({
        branchId: [null],
        stock: [0]
      })
    );
  }

  public removeBranchStock(variantIndex: number, stockIndex: number): void {
    this.getBranchStocks(variantIndex).removeAt(stockIndex);
  }

  public onSubmit(): void {
    this._productFacade.create(this.form.value);
  }

  public closeModal(): void {
    this.closeEvent.emit();
  }

  public removeImage(index: number): void {
    this.getImages(index).clear();
    this.previewImages = EMPTY;
  }

  public onFileSelected(event: Event, index: number): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;
    if (!file.type.startsWith('image/')) {
      return;
    }

    const mediaRequest: MediaRequest = {
      products: 'product',
      file,
      folder: 'products',
      ownerType: OwnerType.Product
    };

    this._mediaHttpClientService
      .upload(mediaRequest)
      .pipe(take(1))
      .subscribe({
        next: mediaResponse => {
          this.getImages(index).push(this._fb.control(mediaResponse.publicId));
          this.previewImages = mediaResponse.url;
          this._cdr.markForCheck();
        },
        error: () => {
          alert('Error subiendo imagen');
        }
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
    return '';
  }

  private _getField(fieldName: string): AbstractControl | null {
    if (fieldName.includes('.')) {
      const [parent, child] = fieldName.split('.');
      const parentGroup = this.form.get(parent);

      return parentGroup ? parentGroup.get(child) : null;
    }
    return this.form.get(fieldName);
  }
}
