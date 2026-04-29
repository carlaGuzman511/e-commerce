import {createFeatureSelector, createSelector} from '@ngrx/store';
import {ProductState} from '../../state/product.state';

export const selectProductState = createFeatureSelector<ProductState>('product');

export const selectProductsByPage = (page: number) =>
  createSelector(selectProductState, state => state.pages[page] || []);

export const selectProductLoading = createSelector(selectProductState, s => s.loading);
