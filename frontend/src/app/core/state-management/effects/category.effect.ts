import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {map, switchMap} from 'rxjs/operators';
import * as CategoryActions from '../actions/category/category.action';
import {CategoryHttpClientService} from '../../services/http/category-http-client.service';
import {Category} from '../../models/category.model';

@Injectable()
export class CategoryEffects {
  loadCategories$ = createEffect(() =>
    this._actions$.pipe(
      ofType(CategoryActions.loadCategories),
      switchMap(() =>
        this._categoryService
          .getAllCategories()
          .pipe(map((categories: Category[]) => CategoryActions.setCategories({categories: categories})))
      )
    )
  );

  create$ = createEffect(() =>
    this._actions$.pipe(
      ofType(CategoryActions.createCategory),
      switchMap(({request}) =>
        this._categoryService.createCategory(request).pipe(map(() => CategoryActions.loadCategories()))
      )
    )
  );

  update$ = createEffect(() =>
    this._actions$.pipe(
      ofType(CategoryActions.updateCategory),
      switchMap(({request}) =>
        this._categoryService.updateCategory(request).pipe(map(() => CategoryActions.loadCategories()))
      )
    )
  );

  constructor(private _actions$: Actions, private _categoryService: CategoryHttpClientService) {}
}
