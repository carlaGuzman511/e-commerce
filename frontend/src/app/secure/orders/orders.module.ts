import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { SharedModule } from "src/app/shared/shared.module";
import { ReactiveFormsModule } from "@angular/forms";
import { OrderFormComponent } from "./components/order-form/order-form.component";
import { OrderContainerComponent } from "./components/order-container/order-container.component";
import { OrderModalComponent } from "./components/order-modal/order-modal.component";
import { OrderCardComponent } from "./components/order-card/order-card.component";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";

@NgModule({
  declarations: [
    OrderCardComponent,
    OrderModalComponent,
    OrderFormComponent,
    OrderContainerComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild([
    { path: 'order', component: OrderContainerComponent },
  ]),
  ],
  providers: []
})
export class OrdersModule {}
