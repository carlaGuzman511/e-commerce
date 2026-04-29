import {createReducer, on} from '@ngrx/store';
import {initialProductState} from '../../state/product.state';
import * as ProductActions from '../../actions/product/product.actions';

export const productReducer = createReducer(
  initialProductState,

  on(ProductActions.loadProductsPage, state => ({
    ...state,
    loading: true,
    error: null
  })),

  on(ProductActions.loadProductsPageSuccess, (state, {page, products, totalElements, totalPages, pageSize}) => ({
    ...state,
    loading: false,
    pages: {
      ...state.pages,
      [page]: products
    },
    totalElements,
    totalPages,
    pageSize
  })),

  on(ProductActions.loadProductsPageFailure, (state, {error}) => ({
    ...state,
    loading: false,
    error
  }))
);
