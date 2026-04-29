import {ChangeDetectionStrategy, Component} from '@angular/core';
import {ButtonType} from '../../shared/enums/button-type.enum';
import {IconPositionButton} from '../../shared/enums/icon-position-button.enum';

@Component({
  selector: 'app-secure-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductsComponent {
  public isOpen: boolean;

  public readonly BUTTON_TYPE = ButtonType;
  public readonly ICON_POSITION = IconPositionButton;

  constructor() {
    this.isOpen = false;
  }

  public openModal(): void {
    this.isOpen = true;
  }

  public closeModal(): void {
    this.isOpen = false;
  }
}
