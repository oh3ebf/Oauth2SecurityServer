import { Component, OnInit, Input } from '@angular/core';
import {AbstractControl} from '@angular/forms';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';

import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';

import { DialogComponent, DialogService, ExDialog, DialogCache, FocusBlurDirective } from '../shared/NgExDialog/dialog.module';
import { UserService } from '../core/users.service';
import { User } from './user';


@Component({
    moduleId: module.id,
    selector: 'app-password-dialog',
    templateUrl: './password.component.html',
    styleUrls: ['./password.component.scss']
})

export class PasswordComponent extends DialogComponent implements OnInit {
    model: any = {
      password: '',
      confirmPassword: ''
    };

    passwordDialogTitle: 'Change Password';
    @Input() user: User;

    constructor(private translateService: TranslateService, private toastyService: ToastyService,
      private userService: UserService, dialogService: DialogService, private exDialog: ExDialog) {
        super(dialogService);
    }

    ngOnInit() {
    }

    changePassword() {
      console.log(this.model.password);
      this.updatePasswd(this.callerData);

      this.result = true;
      this.dialogResult();
    }

    /*
     * Cancel button action
     */
    cancel() {
      this.result = false;
      this.dialogResult();
    }

    /*
     * Make update request and check response
     */
    private updatePasswd(params: any) {
        this.userService.updatePasswd(params).subscribe(
            (response) => {
                let msg = 'TXT_HTTP_UPPDATE_OK';
                this.translateService.get(msg).subscribe((res: string) => {msg = res; });
                this.toastyService.success(msg);

            },
            (error) => {
                let title = 'TXT_HTTP_UPPDATE_FAILED';
                this.translateService.get(title).subscribe((res: string) => {title = res; });
                this.toastyService.error(this.toastMessage(title, error));
            }
        );
    }

    /*
     * Check data type UsersResponse
     */
/*    private isUsersResponse(obj: any): obj is UsersResponse {
        return obj.status !== undefined
    }*/

    /*
     * Create toast parameters
     */
    private toastMessage(title: string, message: string): ToastOptions {
         const toastOptions: ToastOptions = {
            title: title,
            msg: message
        };

        return toastOptions;
    }
}
