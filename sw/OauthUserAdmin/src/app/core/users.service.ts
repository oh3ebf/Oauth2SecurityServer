import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Observable';

import {User} from '../users/user';
import {Credentials} from '../core/authentication/credentials.interface';
import {AuthenticationService} from '../core/authentication/authentication.service';
import {ServiceActionInterfase} from '../shared/service-action.interface';
import {RequestResponse} from '../shared/request-response.interface';

const routes = {
  users: () => `oauthadmin/api/v1/users`,
  user: (u: User) => `oauthadmin/api/v1/users/${u.id}`,
  userPasswd: (u: User) => `oauthadmin/api/v1/users/${u.id}/password`
};

@Injectable()
export class UserService {
  private headers: Headers;
  private options: RequestOptions;

  constructor(private http: Http, private authenticationService: AuthenticationService) {}

  /*
   *
   */
  public getAll(): Observable<RequestResponse<User> | string> {
    this.setHeaders();

    return this.http.get(routes.users(), this.options)
      .map((res: Response) => res.json() as RequestResponse<User>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public getOne(u: User): Observable<RequestResponse<User> | string> {
    this.setHeaders();

    return this.http.get(routes.user(u), this.options)
      .map((res: Response) => res.json() as RequestResponse<User>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public update(u: User): Observable<RequestResponse<User> | string> {
    this.setHeaders();

    const body = JSON.stringify(u);
    // console.log(body);

    return this.http.put(routes.user(u), body, this.options)
      .map((res: Response) => res.json() as RequestResponse<User>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public updatePasswd(u: User): Observable<RequestResponse<User> | string> {
    this.setHeaders();

    const body = JSON.stringify(u);
    // console.log(body);

    return this.http.put(routes.userPasswd(u), body, this.options)
      .map((res: Response) => res.json() as RequestResponse<User>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public add(u: User): Observable<RequestResponse<User> | string> {
    this.setHeaders();
    const body = JSON.stringify(u);
    // console.log(body);

    return this.http.post(routes.users(), body, this.options)
      .map((res: Response) => res.json() as /*UsersResponse*/RequestResponse<User>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public delete(u: User): Observable<RequestResponse<User> | string> {
    this.setHeaders();

    return this.http.delete(routes.user(u), this.options)
      .map((res: Response) => res.json() as RequestResponse<User>)
      .catch(this.handleError);
  }

  private setHeaders() {
    const credentials: Credentials = this.authenticationService.getCredentials();

    // console.log(credentials);
    this.headers = new Headers({
      'Content-Type': 'application/json',
      'Authorization': 'bearer ' + credentials.access_token
    });

    this.options = new RequestOptions({headers: this.headers, cache: false});
  }

  /*
   *
   */
  private handleError(error: any) {
    const errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    console.error(error);
    return Observable.throw(errMsg);
  }
}
