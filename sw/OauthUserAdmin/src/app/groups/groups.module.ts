import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TranslateModule} from '@ngx-translate/core';
import {FormsModule} from '@angular/forms';
import {AgGridModule} from 'ag-grid-angular/main';

import {ButtonRendererComponent} from '../shared/button-renderer.component';
import {CheckBoxRendererComponent} from '../shared/checkbox-renderer.component';

import {SharedModule} from '../shared/shared.module';
import {GroupsRoutingModule} from './groups-routing.module';
import {GroupService} from '../core/groups.service';
import {GroupsComponent} from './groups.component';
import {GroupMembersComponent} from './group-members.component';

@NgModule({
  imports: [
    FormsModule,
    AgGridModule.withComponents([
      ButtonRendererComponent,
      CheckBoxRendererComponent
    ]),
    CommonModule,
    TranslateModule,
    GroupsRoutingModule,
    SharedModule
  ],
  declarations: [
    GroupsComponent, GroupMembersComponent
  ],
  providers: [
    GroupService, GroupMembersComponent
  ],
  entryComponents: [
    GroupMembersComponent
  ]
})
export class GroupsModule {}
