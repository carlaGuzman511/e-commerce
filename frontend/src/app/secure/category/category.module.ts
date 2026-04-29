import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CategoryListComponent} from './components/category-list/category-list.component';
import {RouterModule} from '@angular/router';
import {CategoryComponent} from './components/category.component';
import {SharedModule} from '../../shared/shared.module';
import {CategoryFormComponent} from './components/category-form/category-form.component';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [CategoryComponent, CategoryListComponent, CategoryFormComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: 'category',
        component: CategoryComponent
      }
    ]),
    SharedModule,
    ReactiveFormsModule
  ],
  exports: [CategoryComponent]
})
export class CategoryModule {}
