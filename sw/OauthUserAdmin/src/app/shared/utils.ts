import {TranslateService} from '@ngx-translate/core';
import {ToastOptions} from 'ng2-toasty';

import {UsersResponse} from '../users/users-response.interface';
import {GroupsResponse} from '../groups/groups-response.interface';
import {OauthClientDetailsResponse} from '../oauth-clients/oauth-clients-response.interface';

export class Utils {

  static translate(text: string, translateService: TranslateService) {
    translateService.get(text).subscribe((res: string) => {
      text = res;
      console.log(res);
    });
    return text;
  }

  static toastMessage(title: string, message: string): ToastOptions {
    const toastOptions: ToastOptions = {
      title: title,
      msg: message
    };

    return toastOptions;
  }

  /*
   * Check data type UsersResponse
   */
  static isUsersResponse(obj: any): obj is UsersResponse {
    return obj.status !== undefined;
  }

  /*
   * Check data type GroupsResponse
   */
  static isGroupsResponse(obj: any): obj is GroupsResponse {
    return obj.status !== undefined;
  }


  /*
   * Check data type OauthClientDetailsResponse
   */
  static isOauthClientDetailsResponse(obj: any): obj is OauthClientDetailsResponse {
    return obj.status !== undefined;
  }
}
