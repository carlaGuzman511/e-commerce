import {ChangeDetectionStrategy, Component, EventEmitter, Output} from '@angular/core';
import {Observable} from 'rxjs';
import {CategoryResponse} from '../../../../core/models/response/categories/category-response.model';
import {CategoryFacade} from '../../../../core/state-management/facades/category/category.facade';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CategoryListComponent {
  @Output() editEvent: EventEmitter<CategoryResponse>;

  public categories$: Observable<CategoryResponse[]>;

  constructor(private _categoryFacade: CategoryFacade) {
    this.categories$ = this._categoryFacade.selectCategories();
    this.editEvent = new EventEmitter<CategoryResponse>();
  }

  public trackByCategory(index: number, item: CategoryResponse): number {
    return item.id;
  }

  public editCategory(category: CategoryResponse): void {
    this.editEvent.emit(category);
  }
}
