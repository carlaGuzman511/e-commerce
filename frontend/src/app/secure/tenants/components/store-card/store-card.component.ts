import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, HostListener } from '@angular/core';
import { StoreResponse } from 'src/app/core/models/response/tenant/store-response.interface';

@Component({
  selector: 'app-store-card',
  templateUrl: './store-card.component.html',
  styleUrls: ['./store-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class StoreCardComponent {
  @Input() public store!: StoreResponse;
  @Output() public edit: EventEmitter<StoreResponse>;
  @Output() public remove: EventEmitter<StoreResponse>;
  @Output() public view: EventEmitter<StoreResponse>;

  public isMenuOpen: boolean;

  constructor(private _hostRef: ElementRef<HTMLElement>) {
    this.isMenuOpen = false;
    this.edit = new EventEmitter<StoreResponse>();
    this.remove = new EventEmitter<StoreResponse>();
    this.view = new EventEmitter<StoreResponse>();
  }

  public toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  public onView(): void { this.view.emit(this.store); this.isMenuOpen = false; }
  public onEdit(): void { this.edit.emit(this.store); this.isMenuOpen = false; }
  public onRemove(): void { this.remove.emit(this.store); this.isMenuOpen = false; }

  @HostListener('document:click', ['$event'])
  public onDocumentClick(event: MouseEvent): void {
    const clickedInside = this._hostRef.nativeElement.contains(event.target as Node);
    if (!clickedInside && this.isMenuOpen) {
      this.isMenuOpen = false;
    }
  }
}