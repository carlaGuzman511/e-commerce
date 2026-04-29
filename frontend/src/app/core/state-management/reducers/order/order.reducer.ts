import { createReducer, on } from '@ngrx/store';
import * as OrderActions from 'src/app/core/state-management/actions/order/order.actions';
import { Order } from 'src/app/core/models/order.model';
import { OrderStatus } from 'src/app/core/enums/order-status.enum';

export interface OrderState {
  loading: boolean;
  error: string | null;
  orders: Order[];
  selectedOrder: Order | null;
}

export const initialState: OrderState = {
  loading: false,
  error: null,
  orders: [],
  selectedOrder: null
};

export const orderReducer = createReducer(
  initialState,
  on(OrderActions.loadOrders, state => ({ ...state, loading: true, error: null })),
  on(OrderActions.loadOrdersSuccess, (state, { orders }) => ({ ...state, loading: false, orders })),
  on(OrderActions.loadOrdersFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(OrderActions.createOrder, state => ({ ...state, loading: true, error: null })),
  on(OrderActions.createOrderSuccess, (state, { order }) => ({
    ...state,
    loading: false,
    orders: [...state.orders, order]
  })),
  on(OrderActions.createOrderFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(OrderActions.updateOrder, state => ({ ...state, loading: true, error: null })),
  on(OrderActions.updateOrderSuccess, (state, { order }) => ({
    ...state,
    loading: false,
    orders: state.orders.map(o => o.id === order.id ? order : o)
  })),
  on(OrderActions.updateOrderFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(OrderActions.deleteOrder, state => ({ ...state, loading: true, error: null })),
  on(OrderActions.deleteOrderSuccess, (state, { id }) => ({
    ...state,
    orders: state.orders.map(o => {
      if(o.id === id){
        o.orderStatus = OrderStatus.Cancelled;
      }
      return o;
    })
  })),
  on(OrderActions.deleteOrderFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(OrderActions.loadOrderById, state => ({ ...state, loading: true, error: null })),
  on(OrderActions.loadOrderByIdSuccess, (state, { order }) => ({
    ...state,
    selectedOrder: order
  })),
  on(OrderActions.loadOrderByIdFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(OrderActions.clearSelectedOrder, state => ({ ...state, selectedOrder: null })),
  on(OrderActions.setSelectedOrder, (state, { order }) => ({
      ...state,
      selectedOrder: order
    }))
);