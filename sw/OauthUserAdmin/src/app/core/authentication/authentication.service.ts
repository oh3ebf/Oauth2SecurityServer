import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { Credentials } from './credentials.interface';
import {environment} from '../../../environments/environment';

export interface LoginContext {
  username: string;
  password: string;
  remember?: boolean;
}

const credentialsKey = 'credentials';
const routes = {
    oauthToken: () => `${environment.tokenUrl}oauth/token`
};

/**
 * Provides a base for authentication workflow.
 * The Credentials interface as well as login/logout methods should be replaced with proper implementation.
 */
@Injectable()
export class AuthenticationService {

  private credentials: Credentials;
  private username: string;

  constructor(private http: Http, private router: Router) {
    // this.credentials = JSON.parse(sessionStorage.getItem(credentialsKey) || localStorage.getItem(credentialsKey));
    // console.log(JSON.parse(sessionStorage.getItem(credentialsKey) || localStorage.getItem(credentialsKey)));
  }

  /**
   * Authenticates the user.
   * @param {LoginContext} context The login parameters.
   * @return {Observable<Credentials>} The user credentials.
   */
  public login(context: LoginContext) {
    // TODO mieti miten istunnon muistaminen toteutetaan????
    // used to by pass authentication temporarily
    this.router.navigate(['/'], { replaceUrl: true });

    this.requestToken(context).subscribe(
        (response: Credentials) => {
          console.log(response);

          this.setCredentials(response, context.remember);
          this.username = context.username;
          this.router.navigate(['/'], { replaceUrl: true });
        },
        (error) => {  }
    );
  }

  /**
   * Logs out the user and clear credentials.
   * @return {Observable<boolean>} True if the user was logged out successfully.
   */
  public logout(): Observable<boolean> {
    // Customize credentials invalidation here
    this.setCredentials();
    return Observable.of(true);
  }

  /**
   * Checks is the user is authenticated.
   * @return {boolean} True if the user is authenticated.
   */
  public isAuthenticated(): boolean {
    return !!this.credentials;
    // return true;
  }

  /**
   * Gets the user credentials.
   * @return {Credentials} The user credentials or null if the user is not authenticated.
   */
  public getCredentials(): Credentials {
    return this.credentials;
  }

  /**
   * Gets the login user
   * @return {string=} current login username
   */
  public getLoginUser(): string {
      return this.username;
  }

  /**
   * Sets the user credentials.
   * The credentials may be persisted across sessions by setting the `remember` parameter to true.
   * Otherwise, the credentials are only persisted for the current session.
   * @param {Credentials=} credentials The user credentials.
   * @param {boolean=} remember True to remember credentials across sessions.
   */
  private setCredentials(credentials?: Credentials, remember?: boolean) {
    this.credentials = credentials || null;

    if (credentials) {
      const storage = remember ? localStorage : sessionStorage;
      storage.setItem(credentialsKey, JSON.stringify(credentials));
    } else {
      sessionStorage.removeItem(credentialsKey);
      localStorage.removeItem(credentialsKey);
    }
  }

  /**
   * Request access token
   * @param {LoginContext=} context The login LoginContext
   * @return {Observable<Credentials | string>} credentials or error message
   */
  private requestToken(context: LoginContext): Observable<Credentials | string> {
    const body: URLSearchParams  =  new URLSearchParams();
    body.append('client_id', 'oauth2client');
    body.append('client_secret', 'secret');
    body.append('grant_type', 'password');
    body.append('username', context.username);
    body.append('password', context.password);

    const headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'charset': 'utf-8',
      'Authorization': 'Basic ' + btoa('oauth2client' + ':' + 'secret')
    });

    const options = new RequestOptions({ headers: headers });

    return this.http.post(routes.oauthToken(), body.toString(), options)
      .map((res: Response) => res.json() as Credentials)
      .catch(this.handleError);
  }

  /**
   * Request access token
   * @param {any=} error request error
   * @return {Observable<string>} error message
   */
  private handleError(error: any): Observable<string> {
          const errMsg = (error.message) ? error.message : error.status
            ? `${error.status} - ${error.statusText}` : 'Server error';
          // console.error(errMsg);
          // console.error(error);
          return Observable.throw(errMsg);
  }
}
