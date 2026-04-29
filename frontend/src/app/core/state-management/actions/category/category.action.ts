import {createAction, props} from '@ngrx/store';
import {Category} from '../../../models/category.model';
import {CategoryRequest} from '../../../models/request/categories/category-request.model';

export enum CategoryAction {
  SetCategories = '[Category] Set Categories',
  LoadCategories = '[Category] Load Categories',
  SetSelectedCategory = '[Category] Set Selected Category',
  CreateCategory = '[Category] Create Category',
  UpdateCategory = '[Category] Update Category'
}

export const loadCategories = createAction(CategoryAction.LoadCategories);

export const setCategories = createAction(CategoryAction.SetCategories, props<{categories: Category[]}>());

export const setSelectedCategory = createAction(
  CategoryAction.SetSelectedCategory,
  props<{category: Category | null}>()
);

export const createCategory = createAction(CategoryAction.CreateCategory, props<{request: CategoryRequest}>());

export const updateCategory = createAction(CategoryAction.UpdateCategory, props<{request: CategoryRequest}>());
