import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {Route} from '../core/route.service';
import {extract} from '../core/i18n.service';
import {OauthClientsComponent} from './oauth-clients.component';

const routes: Routes = Route.withShell([
  {
    path: 'oauth-clients',
    component: OauthClientsComponent,
    data: {
      title: extract('Oauth Clients'),
      breadcrumb: 'Sign Up'
    }
  }
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class OauthClientsRoutingModule {}
