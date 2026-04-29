import {createReducer, on} from '@ngrx/store';
import * as StoreActions from '../../actions/tenant/tenant.actions';
import {Store as StoreModel} from '../../../models/store.model';

export interface StoreState {
  loading: boolean;
  error: string | null;
  stores: StoreModel[];
  selectedStore: StoreModel | null;
}

export const initialState: StoreState = {
  loading: false,
  error: null,
  stores: [],
  selectedStore: null
};

export const storeReducer = createReducer(
  initialState,
  on(StoreActions.loadStores, state => ({...state, loading: true, error: null})),
  on(StoreActions.loadStoresSuccess, (state, {stores}) => ({...state, loading: false, stores})),
  on(StoreActions.loadStoresFailure, (state, {error}) => ({...state, loading: false, error})),

  on(StoreActions.createStore, state => ({...state, loading: true, error: null})),
  on(StoreActions.createStoreSuccess, (state, {store}) => ({
    ...state,
    loading: false,
    stores: [...state.stores, store]
  })),
  on(StoreActions.createStoreFailure, (state, {error}) => ({...state, loading: false, error})),

  on(StoreActions.updateStore, state => ({...state, loading: true, error: null})),
  on(StoreActions.updateStoreSuccess, (state, {store}) => ({
    ...state,
    loading: false,
    stores: state.stores.map(s => (s.id === store.id ? store : s))
  })),
  on(StoreActions.updateStoreFailure, (state, {error}) => ({...state, loading: false, error})),

  on(StoreActions.deleteStore, state => ({...state, loading: true, error: null})),
  on(StoreActions.deleteStoreSuccess, (state, {id}) => ({
    ...state,
    stores: state.stores.map(s => 
      s.id === id ? { ...s, active: false } : s
    )
  })),
  on(StoreActions.deleteStoreFailure, (state, {error}) => ({...state, loading: false, error})),

  on(StoreActions.loadStoreById, state => ({...state, loading: true, error: null})),
  on(StoreActions.loadStoreByIdSuccess, (state, {store}) => ({
    ...state,
    selectedStore: store
  })),
  on(StoreActions.loadStoreByIdFailure, (state, {error}) => ({...state, loading: false, error})),

  on(StoreActions.clearSelectedStore, state => ({ ...state, selectedStore: null })),
  on(StoreActions.setSelectedStore, (state, { store }) => ({
    ...state,
    selectedStore: store
  }))
);
