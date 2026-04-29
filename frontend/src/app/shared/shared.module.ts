import {NgModule} from '@angular/core';
import {GenericButtonComponent} from './components/generic-button/generic-button.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {GenericInputFieldComponent} from './components/generic-input-field/generic-input-field.component';
import {GenericTextAreaFieldComponent} from './components/generic-text-area-field/generic-text-area-field.component';
import {GenericSelectFieldComponent} from './components/generic-select-field/generic-select-field.component';
import {GenericCheckboxFieldComponent} from './components/generic-checkbox-field/generic-checkbox-field.component';
import {GenericDateFieldComponent} from './components/generic-date-field/generic-date-field.component';
import {SmallCardComponent} from './components/small-card/small-card.component';
import {LargeCardComponent} from './components/large-card/large-card.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {CategoryIconPipe} from './pipes/category-icon.pipe';
import {CardInfoComponent} from './components/card-info/card-info.component';
import {AlertComponent} from "./components/alert/alert.component";

@NgModule({
  declarations: [
    GenericInputFieldComponent,
    GenericButtonComponent,
    GenericTextAreaFieldComponent,
    GenericSelectFieldComponent,
    GenericCheckboxFieldComponent,
    GenericDateFieldComponent,
    SmallCardComponent,
    LargeCardComponent,
    NavbarComponent,
    CategoryIconPipe,
    CardInfoComponent,
    AlertComponent
  ],
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  exports: [
    GenericButtonComponent,
    GenericInputFieldComponent,
    GenericTextAreaFieldComponent,
    GenericSelectFieldComponent,
    GenericCheckboxFieldComponent,
    GenericDateFieldComponent,
    SmallCardComponent,
    LargeCardComponent,
    NavbarComponent,
    CategoryIconPipe,
    CardInfoComponent,
    AlertComponent
  ]
})
export class SharedModule {}
