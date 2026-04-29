import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginPageComponent} from './components/login/login-page.component';
import {AuthRoutingModule} from './auth-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {SessionService} from '../../core/services/session.service';
import {AuthHttpClientService} from '../../core/services/http/auth-http-client.service';
import {AuthService} from '../../core/services/auth.service';
import {SharedModule} from '../../shared/shared.module';
import {RegisterModalComponent} from './components/register-modal/register-modal.component';

@NgModule({
  declarations: [LoginPageComponent, RegisterModalComponent],
  imports: [CommonModule, AuthRoutingModule, SharedModule, ReactiveFormsModule],
  providers: [SessionService, AuthHttpClientService, AuthService]
})
export class AuthModule {}
