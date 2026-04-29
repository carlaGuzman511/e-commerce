import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { OrderHttpClientService } from 'src/app/core/services/http/order-http-client.service';
import * as OrderActions from 'src/app/core/state-management/actions/order/order.actions';
import { catchError, exhaustMap, map, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';

@Injectable()
export class OrderEffects {
  loadOrders$ = createEffect(() =>
    this._actions$.pipe(
      ofType(OrderActions.loadOrders),
      mergeMap(() =>
        this._orderService.getAll().pipe(
          map((response: any) => {
            const orders = response?.content ?? [];
            return OrderActions.loadOrdersSuccess({ orders })
          }),
          catchError(error => of(OrderActions.loadOrdersFailure({ error })))
        )
      )
    )
  );

  createOrder$ = createEffect(() =>
    this._actions$.pipe(
      ofType(OrderActions.createOrder),
      exhaustMap(({ order }) =>
        this._orderService.create(order).pipe(         
          map((createdOrder) => OrderActions.createOrderSuccess({ order: createdOrder })),
          catchError(error => of(OrderActions.createOrderFailure({ error })))
          )
        )
      )
    );

  loadOrderById$ = createEffect(() =>
    this._actions$.pipe(
      ofType(OrderActions.loadOrderById),
      mergeMap(({ id }) =>
        this._orderService.getById(id).pipe(
          map(order => OrderActions.loadOrderByIdSuccess({ order })),
          catchError(error => of(OrderActions.loadOrderByIdFailure({ error })))
        )
      )
    )
  );

  updateOrder$ = createEffect(() =>
    this._actions$.pipe(
      ofType(OrderActions.updateOrder),
      exhaustMap(({ order }) =>
        this._orderService.update(order).pipe(
          map(updated => OrderActions.updateOrderSuccess({ order: updated })),
          catchError(error => of(OrderActions.updateOrderFailure({ error })))
        )
      )
    )
  );

  deleteOrder$ = createEffect(() =>
    this._actions$.pipe(
      ofType(OrderActions.deleteOrder),
      mergeMap(({ id }) =>
        this._orderService.removeById(id).pipe(
          map(() => OrderActions.deleteOrderSuccess({ id })),
          catchError(error => of(OrderActions.deleteOrderFailure({ error })))
        )
      )
    )
  );

  constructor(private _actions$: Actions, private _orderService: OrderHttpClientService){}
}
