import { Component, OnDestroy, ChangeDetectionStrategy, ChangeDetectorRef, OnInit } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import * as OrderActions from 'src/app/core/state-management/actions/order/order.actions';
import { Order } from 'src/app/core/models/order.model';
import { Actions, ofType } from '@ngrx/effects';
import { ButtonType } from 'src/app/shared/enums/button-type.enum';
import { IconPositionButton } from 'src/app/shared/enums/icon-position-button.enum';
import { ModalMode } from '../../enums/modal-mode.enum';
import { OrderFacade } from 'src/app/core/state-management/facades/order/order.facade';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { OrderStatus } from 'src/app/core/enums/order-status.enum';

@Component({
  selector: 'app-order-container',
  templateUrl: './order-container.component.html',
  styleUrls: ['./order-container.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderContainerComponent implements OnDestroy, OnInit {
  public modalMode: ModalMode;
  public isModalOpen: boolean;
  public query: string;

  public OrderStatus = OrderStatus;
  public ButtonType = ButtonType;
  public IconPositionButton = IconPositionButton;

  private _destroy$: Subject<void>;
  public loading$: Observable<boolean>;
  public orders$: Observable<Order[]>;
  public selectedOrder$: Observable<Order | null>;

  constructor(
    private _actions$: Actions,
    private _cd: ChangeDetectorRef,
    private _orderFacade: OrderFacade,
  ) {
    this.isModalOpen = false;
    this.query = EMPTY;
    this.modalMode = ModalMode.Create;
    this.ButtonType = ButtonType;
    this.IconPositionButton = IconPositionButton;
    
    this.orders$ = this._orderFacade.orders$;
    this.selectedOrder$ = this._orderFacade.selectedOrder$;
    this.loading$ = this._orderFacade.loading$;
    this._destroy$ = new Subject<void>();

    this._actions$.pipe(
      ofType(OrderActions.createOrderSuccess),
      takeUntil(this._destroy$)
    ).subscribe(() => {
      this.isModalOpen = false;
      this._cd.markForCheck();
    });
  }

  public ngOnInit(): void {
    this._initialize();
  }

  public openModal(): void {
    this.modalMode = ModalMode.Create;
    this._orderFacade.clearSelected();
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onModalCancel(): void { 
    this.isModalOpen = false; 
    this._cd.markForCheck(); 
  }

  public onView(order: Order): void {
    if (!order.id) return;

    this.modalMode = ModalMode.View;
    this._orderFacade.setSelectedOrder(order);
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onRemove(order: Order): void {
    if (order.id && confirm(`Are you sure you want to delete order: ${order.id}?`)) {
      this._orderFacade.deleteOrder(order.id);
    }
  }

  public ngOnDestroy(): void {
    this._destroy$.next();
    this._destroy$.complete();
  }

  public trackByOrder(index: number, order: Order): string | undefined {
    return order.id;
  }

  private _initialize(): void {
    this._orderFacade.loadOrders();
  }
}