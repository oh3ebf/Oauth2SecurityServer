import {Component, OnInit} from '@angular/core';
import {SideMenuItemComponent} from './side-menu-item.component';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss']
})
export class SideMenuComponent implements OnInit {
  private items: SideMenuItemComponent[];
  private hidden: boolean;

  constructor() {
    this.hidden = false;
    this.items = [];
  }

  ngOnInit() {
  }

  public getSideMenuItems(): SideMenuItemComponent[] {
    return this.items;
  }

  /*
      constructor(commBroker:CommBroker) {
          this.m_hidden = false;
          this.m_items = [];
          this.m_commBroker = commBroker;
          this.listenWinResize();

      }*/

  /*   private listenWinResize() {
         var self = this;
         self.m_commBroker.onEvent(Consts.Events().WIN_SIZED).subscribe((e:IMessage)=> {
             if (e.message.width < Consts.Values().MENU_MIN_ICON_SHOW){
                 this.m_hidden = true;
             } else {
                 this.m_hidden = false;
             }
         });
     }*/

  public addMenuItem(item: SideMenuItemComponent): void {
    this.items.push(item);
  }

  private listenMenuSelected(/*event*/) {
    /*
    var text = event.target.nodeName.toLocaleLowerCase() == 'li' ? jQuery(event.target).find('span').text() : jQuery(event.target).closest('li').find('span').text();
    event.preventDefault();
    this.m_commBroker.fire({
        fromInstance: self,
        event: Consts.Events().MENU_SELECTION,
        context: null,
        message: text
    });*/
    return false;
  }
}
