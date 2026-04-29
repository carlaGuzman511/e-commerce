import { Store } from '../../models/store.model';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';

export interface StoreState {
  loading: boolean;
  error: string | null;
  stores: Store[];
  selectedStore: Store | null;
}

export const initialState: StoreState = {
  loading: false,
  error: EMPTY,
  stores: [],
  selectedStore: null
};