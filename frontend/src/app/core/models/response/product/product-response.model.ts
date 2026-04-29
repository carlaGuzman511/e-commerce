import {MediaResponse} from '../media/media-response.model';

export interface ProductResponse {
  id: number;
  name: string;
  brand: string;
  description: string;
  technicalDetails: string | null;
  categoryId: number;
  status: string;
  minStock: number | null;
  variants: ProductVariantResponse[];
}

export interface ProductVariantResponse {
  id: number;
  productCode: string;
  description: string | null;
  price: number;
  attributesJson: string;
  branchStocks: ProductBranchStockResponse[];
  images: MediaResponse[];
}

export interface ProductBranchStockResponse {
  branchId: number;
  stock: number;
}
