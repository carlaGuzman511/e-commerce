import {createReducer, on} from '@ngrx/store';
import {CategoryState, initialState} from '../../state/category.state';
import * as CategoryActions from '../../actions/category/category.action';

export const categoryReducer = createReducer(
  initialState,

  on(CategoryActions.setCategories, (state: CategoryState, {categories}) => {
    return {
      ...state,
      categories: categories,
      loading: true
    };
  }),

  on(CategoryActions.setSelectedCategory, (state, {category}) => ({
    ...state,
    selectedCategory: category
  })),

  on(CategoryActions.loadCategories, state => ({
    ...state,
    loading: true
  }))
);
