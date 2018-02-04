import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';
import {AgGridModule} from 'ag-grid-angular/main';

import { OauthClientsRoutingModule } from './oauth-clients-routing.module';
import { OauthClientsComponent } from './oauth-clients.component';




@NgModule({
  imports: [
  FormsModule,
  AgGridModule.withComponents([
  ]),
    CommonModule,
    TranslateModule,
    OauthClientsRoutingModule
  ],
  declarations: [
    OauthClientsComponent
  ],
  providers: [
  ],
  entryComponents: [

  ]
})
export class OauthClientsModule { }
