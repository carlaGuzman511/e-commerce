import { Component, Input, Output, EventEmitter, ChangeDetectionStrategy, ElementRef, HostListener } from '@angular/core';
import { Branch } from 'src/app/core/models/branch.model';

@Component({
  selector: 'app-branch-card',
  templateUrl: './branch-card.component.html',
  styleUrls: ['./branch-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class BranchCardComponent {
  @Input() public branch!: Branch;

  @Output() public edit: EventEmitter<Branch>;
  @Output() public remove: EventEmitter<Branch>;
  @Output() public view: EventEmitter<Branch>;

  public isMenuOpen: boolean;

  constructor(private _hostRef: ElementRef<HTMLElement>) {
    this.isMenuOpen = false;
    this.edit = new EventEmitter<Branch>();
    this.remove = new EventEmitter<Branch>();
    this.view = new EventEmitter<Branch>();
  }

  public toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  public onView(): void { 
    this.view.emit(this.branch); 
    this.isMenuOpen = false; 
  }

  public onEdit(): void { 
    this.edit.emit(this.branch); 
    this.isMenuOpen = false; 
  }
  
  public onRemove(): void { 
    this.remove.emit(this.branch); 
    this.isMenuOpen = false; 
  }

  @HostListener('document:click', ['$event'])
  public onDocumentClick(event: MouseEvent): void {
    const clickedInside = this._hostRef.nativeElement.contains(event.target as Node);
    if (!clickedInside && this.isMenuOpen) {
      this.isMenuOpen = false;
    }
  }
}