import {TranslateService} from '@ngx-translate/core';
import { Observable } from 'rxjs/Observable';

import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';
import { GridOptions } from 'ag-grid';

import { Utils } from './utils';
import { ServiceActionInterfase } from './service-action.interface';


export class ServiceAction<T> {
  constructor() { }

  /**
   * Make get request for all data and check response
   */
  public getAllGridData<T extends ServiceActionInterfase>(toastyService: ToastyService,
    translateService: TranslateService, gridOptions: GridOptions, service: T) {
    service.getAll().subscribe(
     (data: any) => {
         // can be null
         if (gridOptions.api) {
             // update table from response
             gridOptions.api.setRowData(data.response);
          }
      },
      (error) => {
          toastyService.error(Utils.toastMessage(Utils.translate('TXT_HTTP_GET_FAILED', translateService), error));
      }
    );
  }

  /*
   * Make add request
   */
  public addGridData<T extends ServiceActionInterfase>(params: any, toastyService: ToastyService,
    translateService: TranslateService, gridOptions: GridOptions, service: T, rowData: any) {
      service.add(params).subscribe(
          (data) => {
              // show toast to user
              toastyService.success(Utils.translate('TXT_HTTP_ADD_OK', translateService));

              // check response type
              if (Utils.isOauthClientDetailsResponse(data)) {
                  if (data.response.length === 1) {
                      // one client detail data in response, set id and update table
                      rowData.id = data.response[0].id;
                      gridOptions.api.updateRowData({ update: [rowData] });
                  }
              }

          },
          (error) => {
              console.log(error);
              // show toast to user
              toastyService.error(Utils.toastMessage(Utils.translate('TXT_HTTP_ADD_FAILED', translateService), error));
          }
      );
  }

  /**
   * Make update request and check response
   */
  public updateGridData<T extends ServiceActionInterfase>(params: any, toastyService: ToastyService,
    translateService: TranslateService, service: T) {
      service.update(params).subscribe(
          (response: any) => {
              const msg = 'TXT_HTTP_UPPDATE_OK';
              toastyService.success(Utils.translate(msg, translateService));
          },
          (error: string) => {
              toastyService.error(Utils.toastMessage(Utils.translate('TXT_HTTP_UPPDATE_FAILED', translateService), error));
          }
      );
  }

  /*
   * Make delete request
   */
  public deleteGridData<T extends ServiceActionInterfase>(params: any, toastyService: ToastyService,
    translateService: TranslateService, gridOptions: GridOptions, service: T) {
      service.delete(params).subscribe(
          (response) => {
              toastyService.success(Utils.translate('TXT_HTTP_DELETE_OK', translateService));

              const selectedData = gridOptions.api.getSelectedRows();
              gridOptions.api.updateRowData({ remove: selectedData });
          },
          (error) => {
              toastyService.error(Utils.toastMessage(Utils.translate('TXT_HTTP_DELETE_FAILED', translateService), error));
          }
      );
  }
}
