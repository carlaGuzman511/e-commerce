import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CategoryCardsComponent} from './components/category-card/category-cards.component';
import {ProductListComponent} from './components/product/product-list.component';
import {HomeComponent} from './home.component';
import {SharedModule} from '../../shared/shared.module';
import {ProductDetailComponent} from './components/product-detail/product-detail.component';

@NgModule({
  declarations: [CategoryCardsComponent, ProductListComponent, HomeComponent, ProductDetailComponent],
  imports: [CommonModule, SharedModule],
  providers: [],
  exports: [HomeComponent, CategoryCardsComponent, ProductListComponent]
})
export class HomeModule {}
