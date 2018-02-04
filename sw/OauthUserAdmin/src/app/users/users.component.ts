import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FormsModule} from '@angular/forms';

import {GridOptions} from 'ag-grid';
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';

import {User} from './user';
import {PasswordComponent} from './password.component';
import {ButtonRendererComponent} from '../shared/button-renderer.component';
import {CheckBoxRendererComponent} from '../shared/checkbox-renderer.component';
import {DialogComponent, DialogService, ExDialog, DialogCache, FocusBlurDirective} from '../shared/NgExDialog/dialog.module';
import {UserService} from '../core/users.service';
import {Utils} from '../shared/utils';
import {ServiceAction} from '../shared/service-action';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],

})
export class UsersComponent implements OnInit {
  private static passwordButtonText = 'TXT_CHANGE_PASSWORD';

  /*
   * Create columns
   */
  private static createColumnDefs(translateService: TranslateService) {
    return [
      {
        headerName: Utils.translate('TXT_FIRSTNAME', translateService),
        field: 'firstName',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_LASTNAME', translateService),
        field: 'lastName',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_USERNAME', translateService),
        field: 'username',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_EMAIL', translateService),
        field: 'email',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_PHONE', translateService),
        field: 'phone',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_ACCOUNT_STATE', translateService),
        field: 'enabled',
        cellRendererFramework: CheckBoxRendererComponent,
        suppressFilter: true,
        width: 100
      },
      {
        headerName: Utils.translate('TXT_ACCOUNT_EXPIRED', translateService),
        field: 'accountNonExpired',
        cellRendererFramework: CheckBoxRendererComponent,
        suppressFilter: true,
        width: 100
      },
      {
        headerName: Utils.translate('TXT_ACCOUNT_LOCKED', translateService),
        field: 'accountNonLocked',
        cellRendererFramework: CheckBoxRendererComponent,
        suppressFilter: true,
        width: 100
      },
      {
        headerName: Utils.translate('TXT_CREDENTIALS_EXPIRED', translateService),
        field: 'credentialsNonExpired',
        cellRendererFramework: CheckBoxRendererComponent,
        suppressFilter: true,
        width: 100
      },
      {
        headerName: '',
        field: '',
        value: {
          name: Utils.translate(UsersComponent.passwordButtonText, translateService),
          source: UsersComponent.passwordButtonText
        },
        cellRendererFramework: ButtonRendererComponent,
        suppressFilter: true,
        width: 180
      }
    ];
  }

  public gridOptions: GridOptions;
  public selectedPageSize: number;
  private rowSelection: any;
  private newRowData: User;

  private ac: ServiceAction<UserService>;

  constructor(private translateService: TranslateService, private toastyService: ToastyService,
    private userService: UserService, private exDialog: ExDialog) {
    this.ac = new ServiceAction<UserService>();
    this.selectedPageSize = 10;
  }

  public ngOnInit() {
    this.gridOptions = <GridOptions>{
      columnDefs: UsersComponent.createColumnDefs(this.translateService),
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

        this.ac.getAllGridData(this.toastyService, this.translateService, this.gridOptions, this.userService);
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
    this.newRowData = new User();
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
      message: Utils.translate('TXT_DELETE_USER', this.translateService)
    })
      .subscribe((result) => {
        if (result) {
          // send delete
          this.ac.deleteGridData(selectedData[0], this.toastyService, this.translateService,
            this.gridOptions, this.userService);
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
    console.log(params);
    if (params.source === UsersComponent.passwordButtonText) {
      // change password dialog
      this.exDialog.openPrime(PasswordComponent, {width: '450px', draggable: false, callerData: params.data});
    }
  }

  /*
   * Callback from checkbox state change
   */
  public checkboxRendererCallback(params: any) {
    // update request
    this.ac.updateGridData(params, this.toastyService, this.translateService, this.userService);
  }

  /*
   * Callback from row edit
   */
  private rowChanged(event: any) {

    if (event.data.id == null) {
      console.log(event.data.id);
      // add request
      this.ac.addGridData(event.data, this.toastyService, this.translateService,
        this.gridOptions, this.userService, this.newRowData);
    } else {
      console.log(event.data);
      // update request
      this.ac.updateGridData(event.data, this.toastyService, this.translateService, this.userService);
    }
  }
}
