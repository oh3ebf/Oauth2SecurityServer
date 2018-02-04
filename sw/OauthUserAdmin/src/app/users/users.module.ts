import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {FormsModule} from '@angular/forms';
import {AgGridModule} from 'ag-grid-angular/main';

import {ButtonRendererComponent} from '../shared/button-renderer.component';
import {CheckBoxRendererComponent} from '../shared/checkbox-renderer.component';
import {EqualValidator} from '../shared/equal-validator.directive';

import {UsersRoutingModule} from './users-routing.module';
import {UsersComponent} from './users.component';
import {PasswordComponent} from './password.component';
import {UserService} from '../core/users.service';


@NgModule({
  imports: [
    FormsModule,
    AgGridModule.withComponents([
      ButtonRendererComponent,
      CheckBoxRendererComponent
    ]),
    CommonModule,
    TranslateModule,
    UsersRoutingModule
  ],
  declarations: [
    UsersComponent, PasswordComponent, EqualValidator
  ],
  providers: [
    UserService, PasswordComponent
  ],
  entryComponents: [
    PasswordComponent
  ]
})
export class UsersModule {}
