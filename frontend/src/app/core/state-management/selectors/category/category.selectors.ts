import { createFeatureSelector, createSelector } from '@ngrx/store';
import { CategoryState } from '../../state/category.state';

export const selectCategoryState = createFeatureSelector<CategoryState>('category');
export const selectCategory = createSelector(selectCategoryState, state => state.categories);
