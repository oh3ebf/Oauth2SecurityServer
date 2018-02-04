import { NgModule, Optional, SkipSelf } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpModule, Http, XHRBackend, ConnectionBackend, RequestOptions } from '@angular/http';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ShellComponent } from './shell/shell.component';
import { HeaderComponent } from './shell/header/header.component';
import { SideMenuComponent } from './shell/side-menu/side-menu.component';
import { SideMenuItemComponent } from './shell/side-menu/side-menu-item.component';
import { AuthenticationService } from './authentication/authentication.service';
import { AuthenticationGuard } from './authentication/authentication.guard';

import { I18nService } from './i18n.service';
import { HttpService } from './http/http.service';
import { HttpCacheService } from './http/http-cache.service';
import { UserService } from './users.service';
import { GroupService } from './groups.service';
import { OauthClientDetailsService } from './clients-details.service';

export function createHttpService(backend: ConnectionBackend,
                                  defaultOptions: RequestOptions,
                                  httpCacheService: HttpCacheService) {
  return new HttpService(backend, defaultOptions, httpCacheService);
}

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    TranslateModule,
    NgbModule,
    RouterModule
  ],
  declarations: [
    HeaderComponent,
    SideMenuComponent,
    SideMenuItemComponent,
    ShellComponent,
  ],
  providers: [
    AuthenticationService,
    AuthenticationGuard,
    I18nService,
    HttpCacheService,
    { provide: Http, deps: [XHRBackend, RequestOptions, HttpCacheService], useFactory: createHttpService },
    UserService,
    GroupService,
    OauthClientDetailsService
  ]
})
export class CoreModule {

  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    // Import guard
    if (parentModule) {
      throw new Error(`${parentModule} has already been loaded. Import Core module in the AppModule only.`);
    }
  }

}
