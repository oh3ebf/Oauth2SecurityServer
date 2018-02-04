import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {ToastyModule} from 'ng2-toasty';
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { DialogModule } from './shared/NgExDialog/dialog.module';

import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { HomeModule } from './home/home.module';
import { UsersModule } from './users/users.module';
import { GroupsModule } from './groups/groups.module';
import { LoginModule } from './login/login.module';
import { OauthClientsModule } from './oauth-clients/oauth-clients.module';

//

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    TranslateModule.forRoot(),
    NgbModule.forRoot(),
    ToastyModule.forRoot(),
    CoreModule,
    SharedModule,
    HomeModule,
    UsersModule,
    GroupsModule,
    LoginModule,
    OauthClientsModule,
    DialogModule,
    AppRoutingModule
  ],
  declarations: [AppComponent],
  providers: [

],
  bootstrap: [AppComponent]
})
export class AppModule {
    constructor(private toastyService: ToastyService, private toastyConfig: ToastyConfig) {
        this.toastyConfig.theme = 'bootstrap';
        this.toastyConfig.position = 'top-right';
        this.toastyConfig.timeout = 3000;
        }
    }
