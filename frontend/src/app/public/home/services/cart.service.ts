import {Injectable} from '@angular/core';
import {CartItem} from '../interfaces/cart-item.model';

@Injectable({providedIn: 'root'})
export class CartService {
  private _storageKey = 'cart_items';

  public get items(): CartItem[] {
    console.warn(this._getCart());
    return this._getCart();
  }

  public addItem(item: CartItem): void {
    const cart = this._getCart();
    const existing = cart.find(i => i.id === item.id);

    if (existing) {
      existing.quantity += item.quantity;
    } else {
      cart.push(item);
    }

    this._saveCart(cart);
  }

  public remove(id: number): void {
    const cart = this._getCart().filter(i => i.id !== id);
    this._saveCart(cart);
  }

  public updateQuantity(id: number, quantity: number): void {
    const cart = this._getCart();
    const item = cart.find(i => i.id === id);

    if (!item) return;

    item.quantity = Math.max(1, quantity);
    this._saveCart(cart);
  }

  public clear(): void {
    this._saveCart([]);
  }

  public getShipping(): number {
    return 50;
  }

  public getSubtotal(): number {
    return this.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  }

  public getTotal(): number {
    return this._getCart().reduce((sum, i) => sum + i.price * i.quantity, 0);
  }

  private _getCart(): CartItem[] {
    return JSON.parse(localStorage.getItem(this._storageKey) ?? '[]');
  }

  private _saveCart(items: CartItem[]): void {
    localStorage.setItem(this._storageKey, JSON.stringify(items));
  }
}
