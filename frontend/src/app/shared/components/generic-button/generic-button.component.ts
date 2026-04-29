import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { ButtonType } from '../../enums/button-type.enum';
import { EMPTY } from '../../constants/app-const.constant';
import { IconPositionButton } from '../../enums/icon-position-button.enum';

@Component({
  selector: 'generic-button',
  templateUrl: './generic-button.component.html',
  styleUrls: ['./generic-button.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericButtonComponent {
  @Input() public buttonType: ButtonType;
  @Input() public disabled: boolean;
  @Input() public iconClass: string;
  @Input() public iconPosition: IconPositionButton;
  @Input() public title: string;

  @Output() public onClick: EventEmitter<void>;

  public readonly ICON_POSITION = IconPositionButton;

  constructor() {
    this.disabled = false;
    this.iconPosition = IconPositionButton.None;
    this.onClick = new EventEmitter<void>();
    this.buttonType = ButtonType.Primary;
    this.iconClass = EMPTY;
    this.title = EMPTY;
  }

  public handleClick(): void {
    this.onClick.emit();
  }
}
