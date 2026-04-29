import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ButtonType} from '../../../../shared/enums/button-type.enum';
import {IconPositionButton} from '../../../../shared/enums/icon-position-button.enum';
import {ProductHttpClientService} from '../../../../core/services/http/product-http-client.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ProductResponse, ProductVariantResponse} from '../../../../core/models/response/product/product-response.model';
import {Observable} from 'rxjs';
import {SelectOption} from '../../../../shared/interfaces/select-option';
import {map} from 'rxjs/operators';
import {CartService} from '../../services/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    '[style.display]': "'block'",
    '[style.justify-self]': "'center'",
    '[style.padding]': "'2rem'",
    '[class.product-detail-host]': 'true'
  }
})
export class ProductDetailComponent implements OnInit {
  public optionVariant: Observable<SelectOption[]> | undefined;
  public product$: Observable<ProductResponse> | undefined;
  public quantity: number = 1;

  public readonly ICON_POSITION = IconPositionButton;
  public readonly BUTTON_TYPE = ButtonType;

  constructor(
    private _productHttpClientService: ProductHttpClientService,
    private _cartService: CartService,
    private _route: ActivatedRoute,
    private _router: Router
  ) {}

  ngOnInit(): void {
    this._initialize();
  }

  public increment(productVariantResponse: ProductVariantResponse): void {
    if (this.quantity < productVariantResponse.branchStocks[0].stock) {
      this.quantity++;
    }
  }

  public decrement(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  public addToCart(product: ProductResponse): void {
    this._cartService.addItem({
      id: product.id,
      name: product.name,
      price: product.variants[0].price,
      image: product.variants[0].images[0].url ?? null,
      quantity: this.quantity
    });
  }

  private _initialize(): void {
    const id = this._route.snapshot.paramMap.get('id');
    if (!id) return;

    this.product$ = this._productHttpClientService.getById(id);

    this.optionVariant = this.product$.pipe(
      map(product =>
        product.variants.map(variant => ({
          value: variant.id,
          label: variant.description ?? ''
        }))
      )
    );
  }
}
