import {ChangeDetectionStrategy, ChangeDetectorRef, Component} from '@angular/core';
import {CartService} from '../home/services/cart.service';
import {CartItem} from '../home/interfaces/cart-item.model';

@Component({
  selector: 'app-home',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  host: {
    '[style.display]': "'block'",
    '[style.justify-self]': "'center'",
    '[style.padding]': "'2rem'",
    '[class.product-detail-host]': 'true'
  }
})
export class CartComponent {
  public cartItems: CartItem[];

  constructor(private _cartService: CartService, private _cdr: ChangeDetectorRef) {
    this.cartItems = this._cartService.items;
  }

  public get subtotal(): number {
    return this._cartService.getSubtotal();
  }

  public get shipping(): number {
    return this._cartService.getShipping();
  }

  public get total(): number {
    return this._cartService.getTotal();
  }

  public get totalItems(): number {
    return this._cartService.items.reduce((sum, item) => sum + item.quantity, 0);
  }

  public trackByCart(index: number, item: CartItem): number {
    return item.id;
  }

  public decrement(cartItem: CartItem): void {
    const newQuantity = cartItem.quantity - 1;

    if (newQuantity <= 0) return;
    cartItem.quantity = newQuantity;
    this._cartService.updateQuantity(cartItem.id, newQuantity);
    this._cdr.markForCheck();
  }

  public increment(cartItem: CartItem): void {
    const newQuantity = cartItem.quantity + 1;
    cartItem.quantity = newQuantity;
    this._cartService.updateQuantity(cartItem.id, newQuantity);
    this._cdr.markForCheck();
  }

  public deleteItem(cartItem: CartItem): void {
    this._cartService.remove(cartItem.id);
    this.cartItems = this._cartService.items;
  }
}
