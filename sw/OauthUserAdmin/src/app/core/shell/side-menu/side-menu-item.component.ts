import {Component, Input, AfterViewInit} from '@angular/core';
import {SideMenuComponent} from './side-menu.component';

@Component({
  // inputs: ['localTabTitle:tabtitle', 'localFontAwesome:fontAwesome', 'localRouterLink:routerLink'],
  selector: 'app-side-menu-item',
  template: ``
})
export class SideMenuItemComponent implements AfterViewInit {
  @Input() tabtitle: string;
  @Input() fontAwesome: string;
  @Input() routerLink: string;

  public title: 'no name';
  private appMenu: SideMenuComponent;

  constructor(appMenu: SideMenuComponent) {
    this.appMenu = appMenu;
    this.appMenu.addMenuItem(this);
  }

  ngAfterViewInit() {
    const self = this;
  }
}
