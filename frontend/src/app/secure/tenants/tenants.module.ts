import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { StoreFormComponent } from './components/store-form/store-form.component';
import { StoreContainerComponent } from './components/store-container/store-container.component';
import { StoreModalComponent } from './components/store-modal/store-modal.component';
import { BranchCardComponent } from './components/branch-card/branch-card.component';
import { BranchContainerComponent } from './components/branch-container/branch-container.component';
import { BranchFormComponent } from './components/branch-form/branch-form.component';
import { BranchModalComponent } from './components/branch-modal/branch-modal.component';
import { FormsModule } from '@angular/forms';
import { StoreCardComponent } from './components/store-card/store-card.component';

@NgModule({
  declarations: [
    StoreFormComponent,
    StoreContainerComponent,
    StoreModalComponent,
    StoreCardComponent,
    BranchCardComponent,
    BranchContainerComponent,
    BranchFormComponent,
    BranchModalComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule.forChild([
    { path: 'store', component: StoreContainerComponent },
    { path: 'branch', component: BranchContainerComponent },
  ]),
  ]
})
export class TenantsModule { }