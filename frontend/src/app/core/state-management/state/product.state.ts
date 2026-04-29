import {ProductResponse} from '../../models/response/product/product-response.model';

export interface ProductState {
  pages: {
    [page: number]: ProductResponse[];
  };
  totalElements: number;
  totalPages: number;
  pageSize: number;
  loading: boolean;
  error: string | null;
}

export const initialProductState: ProductState = {
  pages: {},
  totalElements: 0,
  totalPages: 0,
  pageSize: 10,
  loading: false,
  error: null
};
