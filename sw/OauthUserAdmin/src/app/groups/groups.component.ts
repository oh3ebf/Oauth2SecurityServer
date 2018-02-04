import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FormsModule} from '@angular/forms';

import {GridOptions} from 'ag-grid';
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';

import {ButtonRendererComponent} from '../shared/button-renderer.component';
import {DialogComponent, DialogService, ExDialog, DialogCache, FocusBlurDirective} from '../shared/NgExDialog/dialog.module';
import {GroupService} from '../core/groups.service';
import {GroupsResponse} from './groups-response.interface';
import {GroupMembersComponent} from './group-members.component';
import {Group} from './group';
import {Utils} from '../shared/utils';
import {ServiceAction} from '../shared/service-action';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements OnInit {
  private static membersButtonText = 'TXT_GROUP_MEMBERS_BUTTON';

  /*
   * Create columns
   */
  private static createColumnDefs(translateService: TranslateService) {
    return [
      {
        headerName: Utils.translate('TXT_GROUP_NAME', translateService),
        field: 'groupName',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_GROUP_DESCRIPTION', translateService),
        field: 'description',
        width: 300,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_GROUP_AUTHORITY', translateService),
        field: 'authority',
        width: 300,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_GROUP_MEMBERS', translateService),
        field: '',
        value: {
          name: Utils.translate(GroupsComponent.membersButtonText, translateService),
          source: GroupsComponent.membersButtonText
        },
        cellRendererFramework: ButtonRendererComponent,
        suppressFilter: true,
        width: 300,
        editable: false
      }
    ];
  }

  public gridOptions: GridOptions;
  public selectedPageSize: number;
  private rowSelection: any;
  private newRowData: Group;
  private ac: ServiceAction<GroupService>;

  constructor(private translateService: TranslateService, private toastyService: ToastyService,
    private groupService: GroupService, private exDialog: ExDialog) {

    this.ac = new ServiceAction<GroupService>();
    this.selectedPageSize = 10;
  }

  ngOnInit() {
    this.gridOptions = <GridOptions>{
      columnDefs: GroupsComponent.createColumnDefs(this.translateService),
      defaultColDef: {filter: 'text'},
      context: {
        componentParent: this
      },
      enableFilter: true,
      floatingFilter: true,
      enableSorting: true,
      editType: 'fullRow',
      rowSelection: 'single',
      pagination: true,
      onGridReady: () => {
        if (this.gridOptions.api) {
          this.gridOptions = this.gridOptions;
        }

        this.ac.getAllGridData(this.toastyService, this.translateService, this.gridOptions, this.groupService);
        // console.log(this.gridOptions);
      },
      onRowValueChanged: (event) => {
        this.rowChanged(event);
      }
    };
  }

  /*
   * Add new row
   */
  public onAddRow() {
    this.newRowData = new Group();
    const res = this.gridOptions.api.updateRowData({add: [this.newRowData]});
  }

  /*
   * remove row
   */
  public onRemoveSelected() {
    const selectedData = this.gridOptions.api.getSelectedRows();

    // conform dialog
    this.exDialog.openConfirm({
      title: Utils.translate('TXT_CONFORM_TITLE', this.translateService),
      message: Utils.translate('TXT_DELETE_USER_GROUP', this.translateService)
    })
      .subscribe((result) => {
        if (result) {
          // send delete
          this.ac.deleteGridData(selectedData[0], this.toastyService, this.translateService,
            this.gridOptions, this.groupService);
        }
      });
  }

  /*
   * rows per page changed
   */
  public onPageSizeChanged() {
    this.gridOptions.api.paginationSetPageSize(Number(this.selectedPageSize));
  }

  /*
   * Callback from ag-grid rendered buttons
   */
  public buttonRendererCallback(params: any) {

    if (params.source === GroupsComponent.membersButtonText) {
      console.log(params);
      // change password dialog
      this.exDialog.openPrime(GroupMembersComponent, {width: '450px', draggable: false, callerData: params.data});
    }
  }

  /*
   * Callback from row edit
   */
  private rowChanged(event: any) {
    // TODO tarvitaanko tälläisenä?
    if (event.data.id == null) {
      console.log(event.data.id);
      // add request
      this.ac.addGridData(event.data, this.toastyService, this.translateService,
        this.gridOptions, this.groupService, this.newRowData);
    } else {
      console.log(event.data);
      // update request
      this.ac.updateGridData(event.data, this.toastyService, this.translateService, this.groupService);
    }
  }
}
