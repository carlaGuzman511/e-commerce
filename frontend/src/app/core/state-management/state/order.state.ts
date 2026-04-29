import { Order } from '../../models/order.model';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';

export interface OrderState {
  loading: boolean;
  error: string | null;
  orders: Order[];
  selectedOrder: Order | null;
}

export const initialState: OrderState = {
  loading: false,
  error: EMPTY,
  orders: [],
  selectedOrder: null
};