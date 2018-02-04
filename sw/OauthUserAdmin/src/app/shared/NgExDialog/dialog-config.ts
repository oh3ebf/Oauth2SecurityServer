export class DialogConfig {
  // Please see properties of calling parameter object in dialog.component.ts.

  // App level settings ----------------
  static topOffset = 60;
  static draggable = true;

  // Animation fade-in time is set in bootstrap.css by default (0.3s).
  // You can overwrite the value in dialog-main.component.css.
  static animation = true;

  // Dialog level settings --------------------
  // Background color can also be set in dialog-main.component.css.
  static grayBackground = true;
  static width = '40%';

  // Animation fade-out time in milliseconds.
  static closeDelay = 500;

  // Fade-out time delay in milliseconds for multiple parent dialogs when closing all together.
  static closeDelayParent = 10;

  static closeByEnter = false;
  static closeByEscape = true;
  static closeByClickOutside = true;

  // Usually dialog-level only:
  static closeAllDialogs = false;
  static closeImmediateParent = false;
  static keepOpenForAction = false;
  static keepOpenForClose = false;

  // Dialog-level exclusive, no default set but listed here for reference.
  // static beforeActionCallback: any = undefined;
  // static beforeCloseCallback: any = undefined;

  // Default values for predefined base type dialogs (message or confirm) only:
  static messageTitle = 'Information';
  static confirmTitle = 'Confirmation';

  // Two kinds of button labels in Parameter object for Opening dialog are:
  // actionButtonLabel
  // closeButtonLabel

  // These are for setting defaults only. If passed from parameter object, use these:
  // actionButtonLabel
  // closeButtonLabel
  // --------------------------------------------------------------------
  // Only singel button should be used for basic message dialog, which uses close button pattern by default.
  // Switch to use action button pattern will change button CSS style and set Observable.result = true.
  static messageActionButtonLabel = '';
  static messageCloseButtonLabel = 'OK';

  static confirmActionButtonLabel = 'Yes';
  static confirmCloseButtonLabel = 'No';
  // End for setting defaults only----------------------------------------

  static showIcon = true;
  static messageIcon = 'info';
  static confirmIcon = 'question';

  // Base type dialog only - no default value set here but list these for references.
  // dialogAddClass
  // headerAddClass
  // titleAddClass
  // bodyAddClass
  // messageAddClass
  // footerAddClass
  // actionButtonAddClass
  // closeButtonAddClass
}
