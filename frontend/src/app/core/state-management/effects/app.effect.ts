import {CategoryEffects} from './category.effect';
import {StoreEffects} from './tenant/store.effects';
import {ProductEffects} from './product.effects';
import {BranchEffects} from './tenant/branch.effect';
import {AuthEffects} from './auth.effects';
import {OrderEffects} from './order/order.effects';

export const APP_EFFECTS = [CategoryEffects, StoreEffects, ProductEffects, BranchEffects, AuthEffects, OrderEffects];
