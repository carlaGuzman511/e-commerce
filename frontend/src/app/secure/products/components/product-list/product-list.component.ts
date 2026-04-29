import {ChangeDetectionStrategy, Component} from '@angular/core';
import {ProductFacade} from '../../../../core/state-management/facades/product/product.facade';
import {Observable} from 'rxjs';
import {ProductResponse} from '../../../../core/models/response/product/product-response.model';

@Component({
  selector: 'app-secure-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductListComponent {
  public products$: Observable<ProductResponse[]>;

  constructor(private _productFacade: ProductFacade) {
    this.products$ = this._productFacade.selectPage(0);
  }

  public trackByCard(index: number, card: ProductResponse) {
    return card.id;
  }
}
