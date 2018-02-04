import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Route } from '../core/route.service';
import { extract } from '../core/i18n.service';
import { UsersComponent } from './users.component';

const routes: Routes = Route.withShell([
  { path: 'users', component: UsersComponent, data: { title: extract('Users'), breadcrumb: 'Sign Up' } }
]);

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class UsersRoutingModule { }
