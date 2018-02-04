import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {FormsModule} from '@angular/forms';

import {GridOptions} from 'ag-grid';
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';

import {DialogComponent, DialogService, ExDialog, DialogCache, FocusBlurDirective} from '../shared/NgExDialog/dialog.module';
import {OauthClientDetailsService} from '../core/clients-details.service';
import {OauthClientDetails} from './oauth-client-details';
import {Utils} from '../shared/utils';
import {ServiceAction} from '../shared/service-action';


@Component({
  selector: 'app-oauth-clients',
  templateUrl: './oauth-clients.component.html',
  styleUrls: ['./oauth-clients.component.scss']
})
export class OauthClientsComponent implements OnInit {
  /*
 * Create columns
 */
  private static createColumnDefs(translateService: TranslateService) {
    return [
      {
        headerName: Utils.translate('TXT_CLIENT_ID', translateService),
        field: 'clientId',
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_SECRET', translateService),
        field: 'clientSecret',
        suppressFilter: true,
        width: 100,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_RESOURCE_ID', translateService),
        field: 'resourceIds',
        suppressFilter: true,
        width: 100,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_SCOPE', translateService),
        field: 'scope',
        suppressFilter: true,
        width: 200,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_AUTHORIZED_GRANT_TYPES', translateService),
        field: 'authorizedGrantTypes',
        suppressFilter: true,
        width: 300,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_WEB_SERVER_REDIRECT_URI', translateService),
        field: 'webServerRedirectUri',
        suppressFilter: true,
        width: 150,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_AUTHORITIES', translateService),
        field: 'authorities',
        suppressFilter: true,
        width: 100,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_ACCESS_TOKEN_VALIDITY', translateService),
        field: 'accessTokenValidity',
        suppressFilter: true,
        width: 100,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_REFRESH_TOKEN_VALIDITY', translateService),
        field: 'refreshTokenValidity',
        suppressFilter: true,
        width: 100,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_ADDITIONAL_INFORMATION', translateService),
        field: 'additionalInformation',
        suppressFilter: true,
        width: 100,
        editable: true
      },
      {
        headerName: Utils.translate('TXT_CLIENT_AUTOAPPROVE', translateService),
        field: 'autoapprove',
        suppressFilter: true,
        width: 100,
        editable: true
      }
    ];
  }

  public gridOptions: GridOptions;
  public selectedPageSize: number;
  private rowSelection: any;
  private newRowData: OauthClientDetails;
  private ac: ServiceAction<OauthClientDetailsService>;

  constructor(private translateService: TranslateService, private toastyService: ToastyService,
    private oauthClientDetailService: OauthClientDetailsService, private exDialog: ExDialog) {
    this.selectedPageSize = 10;
    this.ac = new ServiceAction<OauthClientDetailsService>();
  }

  ngOnInit() {

    this.gridOptions = <GridOptions>{
      columnDefs: OauthClientsComponent.createColumnDefs(this.translateService),
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
        // console.log(this.gridOptions);
        this.ac.getAllGridData(this.toastyService, this.translateService, this.gridOptions, this.oauthClientDetailService);
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
    this.newRowData = new OauthClientDetails();
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
      message: Utils.translate('TXT_DELETE_OAUTH_CLIENT_DETAILS', this.translateService)
    })
      .subscribe((result) => {
        if (result) {
          // send delete
          this.ac.deleteGridData(selectedData[0], this.toastyService, this.translateService,
            this.gridOptions, this.oauthClientDetailService);
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
   * Callback from row edit
   */
  private rowChanged(event: any) {
    // TODO tarvitaanko t‰‰ll‰isen‰?
    if (event.data.id === null) {
      console.log(event.data);
      // add request
      this.ac.addGridData(event.data, this.toastyService, this.translateService,
        this.gridOptions, this.oauthClientDetailService, this.newRowData);
    } else {
      console.log(event.data);
      // update request
      this.ac.updateGridData(event.data, this.toastyService, this.translateService, this.oauthClientDetailService);
    }
  }
}
