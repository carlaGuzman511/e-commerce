import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as OrderActions from 'src/app/core/state-management/actions/order/order.actions';
import { selectOrders, selectLoading, selectSelectedOrder } from 'src/app/core/state-management/selectors/order/order.selectors';
import { Order } from 'src/app/core/models/order.model';

@Injectable({ providedIn: 'root' })
export class OrderFacade {
  public orders$: Observable<Order[]>;
  public selectedOrder$: Observable<Order | null>;
  public loading$: Observable<boolean>;

  constructor(private _store: Store) {
    this.orders$ = this._store.select(selectOrders);
    this.selectedOrder$ = this._store.select(selectSelectedOrder);
    this.loading$ = this._store.select(selectLoading);
  }

  public loadOrders(): void {
    this._store.dispatch(OrderActions.loadOrders());
  }

  public loadOrderById(id: string): void {
    this._store.dispatch(OrderActions.loadOrderById({ id }));
  }

  public createOrder(order: Order): void {
    this._store.dispatch(OrderActions.createOrder({ order }));
  }

  public updateOrder(order: Order): void {
    this._store.dispatch(OrderActions.updateOrder({order}));
  }

  public deleteOrder(id: string): void {
    this._store.dispatch(OrderActions.deleteOrder({ id }));
  }

  public clearSelected(): void {
    this._store.dispatch(OrderActions.clearSelectedOrder());
  }

  public setSelectedOrder(order: Order | null): void {
    this._store.dispatch(OrderActions.setSelectedOrder({ order }));
  }
}