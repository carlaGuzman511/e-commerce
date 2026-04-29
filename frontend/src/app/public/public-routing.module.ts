import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ProductDetailComponent} from './home/components/product-detail/product-detail.component';
import {CartComponent} from './cart/cart.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '?page=0'
  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'product/:id',
    component: ProductDetailComponent
  },
  {
    path: 'cart',
    component: CartComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
