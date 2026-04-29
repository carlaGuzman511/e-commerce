import { Component, EventEmitter, ChangeDetectionStrategy, Input, Output, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { Order } from 'src/app/core/models/order.model';
import { ModalMode } from '../../enums/modal-mode.enum';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';

@Component({
  selector: 'app-order-modal',
  templateUrl: './order-modal.component.html',
  styleUrls: ['./order-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class OrderModalComponent implements AfterViewInit {
  @ViewChild('dialog', { static: true }) private _dialogRef!: ElementRef;

  @Input() public initialData: Order | null;
  @Input() public isOpen: boolean;
  @Input() public mode: ModalMode;
  @Input() public title: string;

  @Output() public cancel: EventEmitter<void>;
  @Output() public submit: EventEmitter<Order>;

  public constructor(){
    this.initialData = null;
    this.isOpen = false;
    this.mode = ModalMode.View;
    this.title = EMPTY;
    
    this.cancel = new EventEmitter<void>();  
    this.submit = new EventEmitter<Order>();
  }

  public ngAfterViewInit(): void {
    try { (this._dialogRef.nativeElement as HTMLElement).focus(); } catch {}
  }

  public get isReadOnly(): boolean {
    return this.mode === ModalMode.View;
  }
  
  public onFormSubmit(payload: Order): void {
    this.submit.emit(payload);
  }

  public onFormCancel(): void {
    this.cancel.emit();
  }

  public backdropClick(ev: MouseEvent): void {
    if ((ev.target as HTMLElement).classList.contains('modal-backdrop')) {
      this.onFormCancel();
    }
  }
}