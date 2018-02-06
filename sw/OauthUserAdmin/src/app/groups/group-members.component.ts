import {Component, OnInit, Input} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';

import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';

import {DialogComponent, DialogService, ExDialog, DialogCache, FocusBlurDirective} from '../shared/NgExDialog/dialog.module';
import {GroupService} from '../core/groups.service';
import {UserService} from '../core/users.service';
import {Group} from './group';
import {User} from '../users/user';
import {ServiceAction} from '../shared/service-action';
import {RequestResponse} from '../shared/request-response.interface';

@Component({
  moduleId: module.id,
  selector: 'app-group-members-dialog',
  templateUrl: './group-members.component.html',
  styleUrls: ['./group-members.component.scss']
})

export class GroupMembersComponent extends DialogComponent implements OnInit {

  keepSorted = true;
  key = 'id';
  display = 'username';
  source: Array<any> = [];
  confirmed: Array<any> = [];
  private ac: ServiceAction<UserService>;

  constructor(private translateService: TranslateService, private toastyService: ToastyService,
    private groupService: GroupService, private userService: UserService, dialogService: DialogService,
    private exDialog: ExDialog) {
    super(dialogService);

    this.ac = new ServiceAction<UserService>();
  }

  ngOnInit() {
    console.log(this.callerData);

    // get users and group members
    this.getAllUsers();
    this.getGroupMembers(this.callerData);
  }

  /**
   * Update group members
   */
  applyGroupMembers() {
    this.updateGroupMembers(this.callerData, this.confirmed);

    this.result = true;
    this.dialogResult();
  }

  /**
   * Cancel button action
   */
  cancel() {
    this.result = false;
    this.dialogResult();
  }

  /**
   * Make get request and check response
   */
  private getAllUsers() {
    this.userService.getAll().subscribe(
      (data: RequestResponse<User>) => {
        console.log(data.response);
        this.source = data.response;
      },
      (error: string) => {
        let title = 'TXT_HTTP_GET_FAILED';
        this.translateService.get(title).subscribe((res: string) => {
          title = res;
        });
        this.toastyService.error(this.toastMessage(title, error));
      }
    );
  }

  /**
   * Make get request and check response
   */
  private getGroupMembers(g: Group) {
    this.groupService.getGroupMembers(g).subscribe(
      (data: RequestResponse<User>) => {
        // console.log(data.response);
        this.confirmed = data.response;
      },
      (error: string) => {
        let title = 'TXT_HTTP_GET_FAILED';
        this.translateService.get(title).subscribe((res: string) => {
          title = res;
        });
        this.toastyService.error(this.toastMessage(title, error));
      }
    );
  }

  /*
   * Make update request and check response
   */

  private updateGroupMembers(g: Group, members: Array<User>) {
    this.groupService.updateGroupMembers(g, members).subscribe(
      (response) => {
        let msg = 'TXT_HTTP_UPPDATE_OK';
        this.translateService.get(msg).subscribe((res: string) => {
          msg = res;
        });
        this.toastyService.success(msg);

      },
      (error) => {
        let title = 'TXT_HTTP_UPPDATE_FAILED';
        this.translateService.get(title).subscribe((res: string) => {
          title = res;
        });
        this.toastyService.error(this.toastMessage(title, error));
      }
    );
  }

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
