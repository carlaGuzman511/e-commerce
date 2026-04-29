import { createSelector, createFeatureSelector } from '@ngrx/store';
import { StoreState } from '../../state/store.state';

export const selectStoreState = createFeatureSelector<StoreState>('store');

export const selectLoading = createSelector(
  selectStoreState,
  state => state.loading
);

export const selectSelectedStore = createSelector(
  selectStoreState,
  state => state.selectedStore
)

export const selectStores = createSelector(
  selectStoreState,
  state => state.stores
);

export const selectError = createSelector(
  selectStoreState,
  state => state.error
);
