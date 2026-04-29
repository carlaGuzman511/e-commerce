import { createAction, props } from '@ngrx/store';
import { Store } from 'src/app/core/models/store.model';

export const setSelectedStore = createAction(
  '[Store] Set Selected Store',
  props<{ store: Store | null }>()
);

export const createStore = createAction(
  '[Store] Create Store',
  props<{ store: Store }>()
);

export const createStoreSuccess = createAction(
  '[Store] Create Store Success',
  props<{ store: Store }>()
);

export const createStoreFailure = createAction(
  '[Store] Create Store Failure',
  props<{ error: string }>()
);

export const loadStores = createAction(
  '[Store] Load Stores'
);

export const loadStoresSuccess = createAction(
  '[Store] Load Stores Success', 
  props<{ stores: Store[] }>()
);

export const loadStoresFailure = createAction(
  '[Store] Load Stores Failure',
  props<{ error: string }>()
);

export const loadStoreById = createAction(
  '[Store] Load Store By Id',
  props<{ id: string}>()
);

export const loadStoreByIdSuccess = createAction(
  '[Store] Load Store By Id Success',
  props<{ store: Store }>()
);

export const loadStoreByIdFailure = createAction(
  '[Store] Load Store By Id Failure',
  props<{ error: string }>()
);

export const updateStore = createAction(
  '[Store] Update Store',
  props<{ store: Store }>()
);

export const updateStoreSuccess = createAction(
  '[Store] Update Store Success',
  props<{ store: Store }>()
);

export const updateStoreFailure = createAction(
  '[Store] Update Store Failure',
  props<{ error: string }>()
);

export const deleteStore = createAction(
  '[Store] Delete Store',
  props<{ id: string }>()
);

export const deleteStoreSuccess = createAction(
  '[Store] Delete Store Success',
  props<{ id: string }>()
);

export const deleteStoreFailure = createAction(
  '[Store] Delete Store Failure',
  props<{ error: string }>()
);

export const clearSelectedStore = createAction(
  '[Store] Clear Selected Store'
);