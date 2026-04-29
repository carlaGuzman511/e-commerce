import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, HostListener } from '@angular/core';
import { Order } from 'src/app/core/models/order.model';
import { OrderStatus } from 'src/app/core/enums/order-status.enum';
@Component({
  selector: 'app-order-card',
  templateUrl: './order-card.component.html',
  styleUrls: ['./order-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderCardComponent {
  @Input() public order!: Order;

  @Output() public remove: EventEmitter<Order>;
  @Output() public view: EventEmitter<Order>;

  public menuOpen = false;
  public orderStatus;

  constructor(private _hostRef: ElementRef<HTMLElement>) {
    this.orderStatus = OrderStatus;
    this.remove = new EventEmitter<Order>();
    this.view = new EventEmitter<Order>();
  }

  public toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  public onView(): void { 
    this.view.emit(this.order); 
    this.menuOpen = false; 
  }
  
  public onRemove(): void { 
    this.remove.emit(this.order); 
    this.menuOpen = false; 
  }

  @HostListener('document:click', ['$event'])
  public onDocumentClick(event: MouseEvent): void {
    const clickedInside = this._hostRef.nativeElement.contains(event.target as Node);
    if (!clickedInside && this.menuOpen) {
      this.menuOpen = false;
    }
  }

  public getStatusClass(status: OrderStatus | string | number): string {
    const value = typeof status === 'number'
      ? OrderStatus[status]
      : status;

    switch (value?.toLowerCase()) {
      case 'pending': return 'pending';
      case 'paid': return 'paid';
      case 'shipped': return 'shipped';
      case 'delivered': return 'delivered';
      case 'cancelled': return 'cancelled';
      case 'refunded': return 'refunded';
      default: return '';
    }
  }
}