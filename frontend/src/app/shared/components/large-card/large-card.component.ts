import {ChangeDetectionStrategy, Component, EventEmitter, Input, Output} from '@angular/core';
import {EMPTY} from '../../constants/app-const.constant';

@Component({
  selector: 'app-large-card',
  templateUrl: './large-card.component.html',
  styleUrls: ['./large-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LargeCardComponent {
  @Input() description?: string;
  @Input() price?: string;
  @Input() category?: string;
  @Input() discountThrough: string;
  @Input() image?: string;
  @Input() isEnablePanelButton: boolean;
  @Input() starPoint?: string;
  @Input() title: string;

  @Output() deleteProduct: EventEmitter<void>;
  @Output() editProduct: EventEmitter<void>;
  @Output() viewProduct: EventEmitter<void>;

  constructor() {
    this.image = EMPTY;
    this.price = EMPTY;
    this.title = EMPTY;
    this.discountThrough = EMPTY;
    this.description = EMPTY;
    this.isEnablePanelButton = false;
    this.editProduct = new EventEmitter<void>();
    this.deleteProduct = new EventEmitter<void>();
    this.viewProduct = new EventEmitter<void>();
  }
}
