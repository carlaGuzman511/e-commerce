import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductsComponent} from './products.component';
import {ProductListComponent} from './components/product-list/product-list.component';
import {SharedModule} from '../../shared/shared.module';
import {ProductFormComponent} from './components/product-form/product-form.component';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [ProductsComponent, ProductListComponent, ProductFormComponent],
  exports: [ProductsComponent, ProductFormComponent],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    RouterModule.forChild([{path: 'product', component: ProductsComponent}])
  ]
})
export class ProductsModule {}
