import { Injectable } from '@angular/core';
import { Store as NgRxStore, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Store } from 'src/app/core/models/store.model';
import * as TenantActions from '../../actions/tenant/tenant.actions';
import { selectStores, selectLoading, selectSelectedStore } from '../../selectors/tenant/store.selectors';

@Injectable({
  providedIn: 'root'
})

export class StoreFacade {
  public stores$: Observable<Store[]>;
  public selectedStore$: Observable<Store | null>;
  public loading$: Observable<boolean>;

  constructor(private _store: NgRxStore) {
    this.stores$ = this._store.pipe(select(selectStores));
    this.selectedStore$ = this._store.pipe(select(selectSelectedStore));
    this.loading$ = this._store.pipe(select(selectLoading));
  }

  public loadStores(): void {
    this._store.dispatch(TenantActions.loadStores());
  }

  public loadStoreById(id: string): void {
    this._store.dispatch(TenantActions.loadStoreById({ id }));
  }

  public createStore(store: Store): void {
    this._store.dispatch(TenantActions.createStore({ store }));
  }

  public updateStore(store: Store): void {
    this._store.dispatch(TenantActions.updateStore({ store }));
  }

  public deleteStore(id: string): void {
    this._store.dispatch(TenantActions.deleteStore({ id }));
  }

  public clearSelectedStore(): void {
    this._store.dispatch(TenantActions.clearSelectedStore());
  }
  
  public setSelectedStore(store: Store | null): void {
    this._store.dispatch(TenantActions.setSelectedStore({ store }));
  }
}