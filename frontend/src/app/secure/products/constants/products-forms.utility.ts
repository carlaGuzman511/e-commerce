import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';

export function getFormProduct(fb: FormBuilder): FormGroup {
  return fb.group({
    name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
    brand: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
    description: ['', [Validators.minLength(10), Validators.maxLength(500)]],
    technicalDetails: ['', [Validators.maxLength(500)]],
    categoryId: [null, Validators.required],
    minStock: [0, [Validators.required, Validators.min(0)]],
    variants: fb.array([])
  });
}

export function getFormVariants(fb: FormBuilder): FormGroup {
  return fb.group({
    productCode: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
    description: ['', [Validators.minLength(3), Validators.maxLength(200)]],
    price: [0, [Validators.required, Validators.min(0)]],
    branchStocks: fb.array([getFormBranchStock(fb)]),
    images: fb.array([], minArrayLength(1))
  });
}

export function getFormBranchStock(fb: FormBuilder): FormGroup {
  return fb.group({
    branchId: ['', Validators.required],
    stock: [0, [Validators.required, Validators.min(0)]]
  });
}

export function getFormAttributes(fb: FormBuilder): FormGroup {
  return fb.group({
    name: ['', [Validators.required, Validators.minLength(1)]],
    value: ['', [Validators.required, Validators.minLength(1)]]
  });
}

export function minArrayLength(min: number) {
  return (control: AbstractControl): ValidationErrors | null => {
    if (control.value && Array.isArray(control.value) && control.value.length >= min) {
      return null;
    }
    return {minArrayLength: {requiredLength: min}};
  };
}
