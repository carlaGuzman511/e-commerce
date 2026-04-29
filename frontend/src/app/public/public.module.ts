import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PublicRoutingModule} from './public-routing.module';
import {PublicLayoutComponent} from './layouts/public-layout.component';
import {HomeModule} from './home/home.module';
import {SharedModule} from '../shared/shared.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CartModule} from './cart/cart.module';

@NgModule({
  declarations: [PublicLayoutComponent],
  imports: [CommonModule, PublicRoutingModule, HomeModule, SharedModule, FormsModule, ReactiveFormsModule, CartModule]
})
export class PublicModule {}
