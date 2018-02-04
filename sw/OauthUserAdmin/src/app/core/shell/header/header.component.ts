import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from '../../authentication/authentication.service';
import {I18nService} from '../../i18n.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  menuHidden = true;

  constructor(private router: Router,
    private authenticationService: AuthenticationService,
    private i18nService: I18nService) {}

  ngOnInit() {}

  public toggleMenu() {
    this.menuHidden = !this.menuHidden;
  }

  public setLanguage(language: string) {
    this.i18nService.language = language;
  }

  public logout() {
    this.authenticationService.logout()
      .subscribe(() => this.router.navigate(['/login'], {replaceUrl: true}));
  }

  public getCurrentLanguage(): string {
    return this.i18nService.language;
  }

  public getLanguages(): string[] {
    return this.i18nService.supportedLanguages;
  }

  public getUsername(): string {
    if (this.authenticationService.isAuthenticated()) {
      return this.authenticationService.getLoginUser();
    }

    return '';
  }

}
