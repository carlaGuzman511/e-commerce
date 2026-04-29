import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { EMPTY } from '../../constants/app-const.constant';

@Component({
  selector: 'app-small-card',
  templateUrl: './small-card.component.html',
  styleUrls: ['./small-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SmallCardComponent {
  @Input() public description: string;
  @Input() public discount?: string;
  @Input() public image: string;
  @Input() public title: string;

  @Output() public viewProduct: EventEmitter<void>;

  constructor() {
    this.description = EMPTY;
    this.image = EMPTY;
    this.viewProduct = new EventEmitter<void>();
    this.title = EMPTY;
  }
}
