import {isDevMode, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {reducers} from './state-management/reducers/app.reducers';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {APP_EFFECTS} from './state-management/effects/app.effect';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {TenantInterceptor} from './interceptors/tenant.Interceptor';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    StoreModule.forRoot(reducers),
    EffectsModule.forRoot(APP_EFFECTS),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: !isDevMode()
    })
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: TenantInterceptor, multi: true }]
})
export class CoreModule {}
