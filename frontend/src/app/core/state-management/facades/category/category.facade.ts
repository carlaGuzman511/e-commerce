import {Injectable} from '@angular/core';
import {Store} from '@ngrx/store';
import {AppState} from '../../app.state';
import * as CategoryActions from '../../actions/category/category.action';
import {Category} from '../../../models/category.model';
import {selectCategory} from '../../selectors/category/category.selectors';
import {Observable} from 'rxjs';
import {CategoryResponse} from '../../../models/response/categories/category-response.model';
import {CategoryRequest} from '../../../models/request/categories/category-request.model';

@Injectable({providedIn: 'root'})
export class CategoryFacade {
  constructor(private _store: Store<AppState>) {}

  public setCategories(categories: Category[]): void {
    this._store.dispatch(CategoryActions.setCategories({categories: categories}));
  }

  public loadCategories(): void {
    this._store.dispatch(CategoryActions.loadCategories());
  }

  public selectCategories(): Observable<CategoryResponse[]> {
    return this._store.select(selectCategory);
  }

  public create(request: CategoryRequest): void {
    this._store.dispatch(CategoryActions.createCategory({request}));
  }

  public update(id: number, request: CategoryRequest): void {
    this._store.dispatch(CategoryActions.updateCategory({request}));
  }
}
