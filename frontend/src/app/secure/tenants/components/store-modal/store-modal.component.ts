import { Component, ChangeDetectionStrategy, EventEmitter, Input, Output, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { ModalMode } from 'src/app/core/enums/modal-mode.enum';
import { Store } from 'src/app/core/models/store.model';

@Component({
  selector: 'app-store-modal',
  templateUrl: './store-modal.component.html',
  styleUrls: ['./store-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StoreModalComponent implements AfterViewInit {
  @Input() public initialData: Store | null;
  @Input() public isOpen: boolean;
  @Input() public title: string;
  @Input() public mode: ModalMode;
  
  @Output() public save: EventEmitter<Store>;
  @Output() public cancel: EventEmitter<void>;

  @ViewChild('dialog', { static: true }) private _dialogRef!: ElementRef;

  public constructor() {
    this.title = EMPTY;
    this.isOpen = false;
    this.mode = ModalMode.Create;
    this.initialData = null;
    this.save = new EventEmitter<Store>();
    this.cancel = new EventEmitter<void>();
  }

  public ngAfterViewInit(): void {
    try { (this._dialogRef.nativeElement as HTMLElement).focus(); } catch {}
  }

  public get isReadOnly(): boolean {
    return this.mode === ModalMode.View;
  }
  
  public onFormSubmit(payload: Store): void {
    this.save.emit(payload);
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