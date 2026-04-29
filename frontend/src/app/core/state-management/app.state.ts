import {ProductState} from './state/product.state';
import {CategoryState} from './state/category.state';
import {StoreState} from './state/store.state';
import {BranchState} from './state/branch.state';
import {AuthState} from './state/auth.state';
import {OrderState} from "./reducers/order/order.reducer";
export interface AppState {
  product: ProductState;
  store: StoreState;
  category: CategoryState;
  order: OrderState;
  branch: BranchState;
  auth: AuthState;
}
