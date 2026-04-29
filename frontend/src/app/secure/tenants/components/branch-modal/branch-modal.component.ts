import { Component, EventEmitter, ChangeDetectionStrategy, Input, Output, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { ModalMode } from 'src/app/core/enums/modal-mode.enum';
import { Branch } from 'src/app/core/models/branch.model';
@Component({
  selector: 'app-branch-modal',
  templateUrl: './branch-modal.component.html',
  styleUrls: ['./branch-modal.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BranchModalComponent implements AfterViewInit {
  @ViewChild('dialog', { static: true }) private _dialogRef!: ElementRef;

  @Input() public initialData: Branch | null;
  @Input() public isOpen: boolean;
  @Input() public title: string;
  @Input() public mode: ModalMode;
  
  @Output() public save: EventEmitter<Branch>;
  @Output() public cancel: EventEmitter<void>;

  public constructor() {
    this.title = EMPTY;
    this.isOpen = false;
    this.mode = ModalMode.Create;
    this.initialData = null;
    this.save = new EventEmitter<Branch>();
    this.cancel = new EventEmitter<void>();
  }

  ngAfterViewInit(): void {
    try { (this._dialogRef.nativeElement as HTMLElement).focus(); } catch {}
  }

  public get isReadOnly(): boolean {
    return this.mode === ModalMode.View;
  }
  
  public onFormSubmit(payload: Branch): void {
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