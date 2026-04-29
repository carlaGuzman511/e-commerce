export interface ProductCreateRequest {
  name: string;
  brand: string;
  description?: string | null;
  technicalDetails?: string | null;
  categoryId: number;
  minStock?: number | null;
  variants: ProductVariantCreateRequest[];
}

export interface ProductVariantCreateRequest {
  productCode: string;
  description?: string | null;
  price: number;
  branchStocks: BranchStockRequest[];
  images?: string[];
  attributes?: Record<string, string>;
}

export interface BranchStockRequest {
  branchId: number;
  stock: number;
}
