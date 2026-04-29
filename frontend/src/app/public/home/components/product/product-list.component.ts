import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {ProductResponse} from '../../../../core/models/response/product/product-response.model';
import {Observable, of} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-product',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductListComponent {
  @Input() public products$: Observable<ProductResponse[]>;

  constructor(private _router: Router, private _route: ActivatedRoute) {
    this.products$ = of([]);
  }

  public trackByCard(index: number, product: ProductResponse): number {
    return product.id;
  }

  public viewProduct(productId: number) {
    this._router.navigate(['product', productId], {
      relativeTo: this._route
    });
  }
}
