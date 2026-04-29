import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginPageComponent} from './components/login/login-page.component';
import {RegisterModalComponent} from './components/register-modal/register-modal.component';

const routes: Routes = [
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterModalComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {}
