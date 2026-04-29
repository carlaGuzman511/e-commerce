import {ActionReducerMap} from '@ngrx/store';
import {AppState} from '../app.state';
import {productReducer} from './product/product.reducer';
import {categoryReducer} from './category/category.reducer';
import {storeReducer} from './tenant/store.reducer';
import {branchReducer} from './tenant/branch.reducer';
import {authReducer} from './auth/auth.reducer';
import { orderReducer } from './order/order.reducer';

export const reducers: ActionReducerMap<AppState> = {
  category: categoryReducer,
  product: productReducer,
  branch: branchReducer,
  auth: authReducer,
  store: storeReducer,
  order: orderReducer,
};
