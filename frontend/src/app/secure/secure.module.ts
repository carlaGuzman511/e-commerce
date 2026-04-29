import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TenantsModule} from './tenants/tenants.module';
import {OrdersModule} from './orders/orders.module';
import {ProductsModule} from './products/products.module';
import {CategoryModule} from './category/category.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, TenantsModule, OrdersModule, ProductsModule, CategoryModule]
})
export class SecureModule {}
