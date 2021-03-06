import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {Route} from '../core/route.service';
import {extract} from '../core/i18n.service';
import {GroupsComponent} from './groups.component';

const routes: Routes = Route.withShell([
  {path: 'groups', component: GroupsComponent, data: {title: extract('Groups'), breadcrumb: 'Groups'}}
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class GroupsRoutingModule {}
