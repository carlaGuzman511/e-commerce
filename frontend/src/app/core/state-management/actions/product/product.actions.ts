import {createAction, props} from '@ngrx/store';
import {ProductResponse} from '../../../models/response/product/product-response.model';
import {ProductCreateRequest} from '../../../models/request/product/product-create-request.model';

export enum ProductAction {
  LoadProductsPage = '[Product] Load Products Page',
  LoadProductsPageSuccess = '[Product] Load Products Page Success',
  LoadProductsPageFailure = '[Product] Load Products Page Failure',
  CreateProduct = '[Product] Create Product',
  CreateProductSuccess = '[Product] Create Product Success',
  CreateProductFailure = '[Product] Create Product Failure'
}

export const loadProductsPage = createAction(ProductAction.LoadProductsPage, props<{page: number}>());

export const loadProductsPageSuccess = createAction(
  ProductAction.LoadProductsPageSuccess,
  props<{
    page: number;
    products: ProductResponse[];
    totalElements: number;
    totalPages: number;
    pageSize: number;
  }>()
);

export const loadProductsPageFailure = createAction(ProductAction.LoadProductsPageFailure, props<{error: string}>());

export const createProduct = createAction(ProductAction.CreateProduct, props<{request: ProductCreateRequest}>());

export const createProductSuccess = createAction(
  ProductAction.CreateProductSuccess,
  props<{product: ProductResponse}>()
);

export const createProductFailure = createAction(ProductAction.CreateProductFailure, props<{error: string}>());
