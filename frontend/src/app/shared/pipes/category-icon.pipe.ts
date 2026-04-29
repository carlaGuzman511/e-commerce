import {Pipe, PipeTransform} from '@angular/core';
import {CategoryIcon} from '../enums/category-icon.enum';
import {CATEGORY_ICON_MAP} from '../constants/category-icon.const';

@Pipe({
  name: 'categoryIcon'
})
export class CategoryIconPipe implements PipeTransform {
  transform(type: string | CategoryIcon): string {
    return CATEGORY_ICON_MAP[type as CategoryIcon] ?? 'icon-cart-light';
  }
}
