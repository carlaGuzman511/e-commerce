import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {Store} from '@ngrx/store';
import {of} from 'rxjs';
import {catchError, map, switchMap, withLatestFrom} from 'rxjs/operators';
import {ProductHttpClientService} from '../../services/http/product-http-client.service';
import * as ProductActions from '../actions/product/product.actions';
import {selectProductState} from '../selectors/product/product.selectors';

@Injectable()
export class ProductEffects {
  loadProductsPage$ = createEffect(() =>
    this._actions$.pipe(
      ofType(ProductActions.loadProductsPage),
      withLatestFrom(this._store.select(selectProductState)),
      switchMap(([{page}, state]) => {
        if (state.pages[page]) {
          return of(
            ProductActions.loadProductsPageSuccess({
              page,
              products: state.pages[page],
              totalElements: state.totalElements,
              totalPages: state.totalPages,
              pageSize: state.pageSize
            })
          );
        }

        return this._productService.getActiveProductsWithStock(page, state.pageSize).pipe(
          map(response =>
            ProductActions.loadProductsPageSuccess({
              page,
              products: response.content,
              totalElements: response.totalElements,
              totalPages: response.totalPages,
              pageSize: response.pageSize
            })
          ),
          catchError(error => of(ProductActions.loadProductsPageFailure({error: error.message})))
        );
      })
    )
  );

  createProduct$ = createEffect(() =>
    this._actions$.pipe(
      ofType(ProductActions.createProduct),
      switchMap(({request}) =>
        this._productService.createProduct(request).pipe(
          map(product => ProductActions.createProductSuccess({product})),
          catchError(error => of(ProductActions.createProductFailure({error: error.message})))
        )
      )
    )
  );

  constructor(private _actions$: Actions, private _productService: ProductHttpClientService, private _store: Store) {}
}
