import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from './shared/shared.module';

@NgModule({
  declarations: [AppComponent],
  imports: [ReactiveFormsModule, BrowserModule, AppRoutingModule, CoreModule, SharedModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
