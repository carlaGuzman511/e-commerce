import {Category} from '../../models/category.model';
import {CategoryResponse} from '../../models/response/categories/category-response.model';

export interface CategoryState {
  categories: CategoryResponse[];
  selectedCategory: Category | null;
  loading: boolean;
}

export const initialState: CategoryState = {
  categories: [],
  selectedCategory: null,
  loading: false
};
