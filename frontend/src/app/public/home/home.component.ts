import {ChangeDetectionStrategy, Component} from '@angular/core';
import {CategoryFacade} from '../../core/state-management/facades/category/category.facade';
import {Observable} from 'rxjs';
import {CategoryResponse} from '../../core/models/response/categories/category-response.model';
import {ProductResponse} from '../../core/models/response/product/product-response.model';
import {ProductFacade} from '../../core/state-management/facades/product/product.facade';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent {
  public categoryTitle: string;
  public categories$: Observable<CategoryResponse[]>;
  public productTitle: string;
  public products$: Observable<ProductResponse[]>;

  constructor(private _productFacade: ProductFacade, private _categoryFacade: CategoryFacade) {
    this._productFacade.loadPage(0);
    this.products$ = this._productFacade.selectPage(0);
    this.categories$ = this._categoryFacade.selectCategories();
    this.categoryTitle = 'Explore categories';
    this.productTitle = 'Featured Product';
  }
}
