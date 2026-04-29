import {ChangeDetectionStrategy, Component} from '@angular/core';
import {CategoryRequest} from '../../../core/models/request/categories/category-request.model';
import {CategoryFacade} from '../../../core/state-management/facades/category/category.facade';
import {ButtonType} from '../../../shared/enums/button-type.enum';
import {IconPositionButton} from '../../../shared/enums/icon-position-button.enum';
import {CategoryResponse} from '../../../core/models/response/categories/category-response.model';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CategoryComponent {
  public isOpenCreate: boolean;
  public isOpenUpdate: boolean;
  public category: CategoryRequest | null | undefined;

  public readonly BUTTON_TYPE = ButtonType;
  public readonly ICON_POSITION = IconPositionButton;

  constructor(private _categoryFacade: CategoryFacade) {
    this.isOpenCreate = false;
    this.isOpenUpdate = false;
  }

  public onSubmit(req: CategoryRequest) {
    console.warn(req);
    if (req.id) {
      this._categoryFacade.update(req.id, req);
    } else {
      this._categoryFacade.create(req);
    }
  }

  public closeModalCreate() {
    this.isOpenCreate = false;
    this.isOpenUpdate = false;
  }

  public openModalCreate(): void {
    this.isOpenCreate = true;
  }

  public openModalUpdate(category: CategoryResponse): void {
    this.category = {
      id: category.id,
      name: category.name,
      icon: category.icon,
      description: category.description,
      active: true
    };
    this.isOpenUpdate = true;
  }
}
