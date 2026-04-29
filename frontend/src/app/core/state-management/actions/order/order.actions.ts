import { createAction, props } from '@ngrx/store';
import { Order } from 'src/app/core/models/order.model';

export const createOrder = createAction(
  '[Order] Create Order',
  props<{ order: Order }>()
);

export const createOrderSuccess = createAction(
  '[Order] Create Order Success',
  props<{ order: Order }>()
);

export const createOrderFailure = createAction(
  '[Order] Create Order Failure',
  props<{ error: string }>()
);

export const loadOrders = createAction(
  '[Order] Load Orders'
);

export const loadOrdersSuccess = createAction(
  '[Order] Load Orders Success', 
  props<{ orders: Order[] }>()
);

export const loadOrdersFailure = createAction(
  '[Order] Load Orders Failure',
  props<{ error: string }>()
);

export const loadOrderById = createAction(
  '[Order] Load Order By Id',
  props<{ id: string}>()
);

export const loadOrderByIdSuccess = createAction(
  '[Order] Load Order By Id Success',
  props<{ order: Order}>()
);

export const loadOrderByIdFailure = createAction(
  '[Order] Load Order By Id Failure',
  props<{ error: string}>()
);

export const updateOrder = createAction(
  '[Order] Update Order',
  props<{ order: Order }>()
);

export const updateOrderSuccess = createAction(
  '[Order] Update Order Success',
  props<{ order: Order }>()
);

export const updateOrderFailure = createAction(
  '[Order] Update Order Failure',
  props<{ error: string }>()
);

export const deleteOrder = createAction(
  '[Order] Delete Order',
  props<{ id: string }>()
);

export const deleteOrderSuccess = createAction(
  '[Order] Delete Order Success',
  props<{ id: string }>()
);

export const deleteOrderFailure = createAction(
  '[Order] Delete Order Failure',
  props<{ error: string }>()
);

export const clearSelectedOrder = createAction(
  '[Order] Clear Selected Order'
);

export const setSelectedOrder = createAction(
  '[Branch] Set Selected Order',
  props<{ order: Order | null }>()
);