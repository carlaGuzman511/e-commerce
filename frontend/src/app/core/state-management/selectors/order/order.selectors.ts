import { createSelector, createFeatureSelector } from '@ngrx/store';
import { OrderState } from '../../reducers/order/order.reducer';

export const selectOrderState = createFeatureSelector<OrderState>('order');

export const selectLoading = createSelector(
  selectOrderState,
  state => state.loading
);

export const selectSelectedOrder = createSelector(
  selectOrderState,
  state => state.selectedOrder
)

export const selectOrders = createSelector(
  selectOrderState,
  state => state.orders
);

export const selectError = createSelector(
  selectOrderState,
  state => state.error
);