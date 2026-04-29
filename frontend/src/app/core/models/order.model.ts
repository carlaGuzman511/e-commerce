import { OrderStatus } from '../enums/order-status.enum';
import { DeliveryMethod } from '../enums/delivery-method.enum';
import { PaymentMethod } from '../enums/payment-method.enum';
import { OrderItem } from './response/order-item.model';
import { Address } from './address.model';

export interface Order {
  id?: string;
  branchId?: string;
  orderStatus: OrderStatus;
  userId?: number;
  deliveryMethod?: DeliveryMethod;
  paymentMethod?: PaymentMethod;
  total?: number;
  discounts?: number;
  items?: OrderItem[];
  shippingAddress?: Address;
}