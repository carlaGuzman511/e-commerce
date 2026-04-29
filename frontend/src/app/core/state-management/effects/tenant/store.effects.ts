import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as StoreActions from '../../actions/tenant/tenant.actions';
import { StoreHttpClientService } from 'src/app/core/services/http/store-http-client.service';
import { catchError, exhaustMap, map, mergeMap } from 'rxjs/operators';
import { of } from 'rxjs';
@Injectable()
export class StoreEffects {
  loadStores$ = createEffect(() =>
    this._actions$.pipe(
      ofType(StoreActions.loadStores),
      mergeMap(() =>
        this._storeService.getAll().pipe(
          map((response: any) => {
            const stores = response?.content ?? [];
            return StoreActions.loadStoresSuccess({ stores })
          }),
          catchError(error => of(StoreActions.loadStoresFailure({ error })))
        )
      )
    )
  );

  createStore$ = createEffect(() =>
    this._actions$.pipe(
      ofType(StoreActions.createStore),
      exhaustMap(({ store }) =>
        this._storeService.create(store).pipe(         
          map((response: any) => {
            const store = response?.content;
            return StoreActions.createStoreSuccess({ store })
          }),
          catchError(error => of(StoreActions.createStoreFailure({ error })))
          )
        )
      )
    );

  loadStoreById$ = createEffect(() =>
    this._actions$.pipe(
      ofType(StoreActions.loadStoreById),
      mergeMap(({ id }) =>
        this._storeService.getById(id).pipe(
          map((response: any) => {
            const store = response?.content;
            return StoreActions.loadStoreByIdSuccess({ store })
          }),
          catchError(error => of(StoreActions.loadStoresFailure({ error })))
        )
      )
    )
  );

  updateStore$ = createEffect(() =>
    this._actions$.pipe(
      ofType(StoreActions.updateStore),
      exhaustMap(({ store }) =>
        this._storeService.update(store).pipe(
          map((response: any) => {
            const store = response?.content;
            return StoreActions.updateStoreSuccess({ store })
          }),
          catchError(error => of(StoreActions.updateStoreFailure({ error })))
        )
      )
    )
  );

  deleteStore$ = createEffect(() =>
    this._actions$.pipe(
      ofType(StoreActions.deleteStore),
      mergeMap(({ id }) =>
        this._storeService.removeById(id).pipe(
          map(() => StoreActions.deleteStoreSuccess({ id })),
          catchError(error => of(StoreActions.deleteStoreFailure({ error })))
        )
      )
    )
  );

  constructor(private _actions$: Actions, private _storeService: StoreHttpClientService){}
}