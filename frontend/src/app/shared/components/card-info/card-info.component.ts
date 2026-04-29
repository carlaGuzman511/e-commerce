import {ChangeDetectionStrategy, Component, Input} from '@angular/core';
import {EMPTY} from '../../constants/app-const.constant';

@Component({
  selector: 'app-card-info',
  templateUrl: './card-info.component.html',
  styleUrls: ['./card-info.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardInfoComponent {
  @Input() public icon: string | undefined;
  @Input() public title: string;

  constructor() {
    this.title = EMPTY;
  }
}
