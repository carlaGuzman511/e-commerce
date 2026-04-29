import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {createProduct, loadProductsPage} from '../../actions/product/product.actions';
import {Observable} from 'rxjs';
import {ProductResponse} from '../../../models/response/product/product-response.model';
import {selectProductLoading, selectProductsByPage} from '../../selectors/product/product.selectors';
import {ProductCreateRequest} from '../../../models/request/product/product-create-request.model';

@Injectable({providedIn: 'root'})
export class ProductFacade {
  public loading$: Observable<boolean> = this._store.select(selectProductLoading);

  constructor(private _store: Store) {}

  public loadPage(page: number): void {
    this._store.dispatch(loadProductsPage({page}));
  }

  public selectPage(page: number): Observable<ProductResponse[]> {
    return this._store.select(selectProductsByPage(page));
  }

  public create(request: ProductCreateRequest): void {
    this._store.dispatch(createProduct({request}));
  }
}
