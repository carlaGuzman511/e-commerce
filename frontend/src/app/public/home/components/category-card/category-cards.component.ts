import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {Observable, of} from 'rxjs';
import {CategoryResponse} from '../../../../core/models/response/categories/category-response.model';

@Component({
  selector: 'app-category',
  templateUrl: './category-cards.component.html',
  styleUrls: ['./category-cards.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CategoryCardsComponent {
  @Input() public categories$: Observable<CategoryResponse[]>;

  constructor() {
    this.categories$ = of([]);
  }

  public trackByCategory(index: number, item: CategoryResponse): number {
    return item.id;
  }
}
