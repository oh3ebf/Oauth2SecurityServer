import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Observable';

import {Group} from '../groups/group';
import {User} from '../users/user';
import {Credentials} from '../core/authentication/credentials.interface';
import {AuthenticationService} from '../core/authentication/authentication.service';
import {ServiceActionInterfase} from '../shared/service-action.interface';
import {RequestResponse} from '../shared/request-response.interface';

const routes = {
  groups: () => `oauthadmin/api/v1/groups`,
  group: (g: Group) => `oauthadmin/api/v1/groups/${g.id}`,
  groupMembers: (g: Group) => `oauthadmin/api/v1/groups/${g.id}/members`
};

@Injectable()
export class GroupService implements ServiceActionInterfase {
  private headers: Headers;
  private options: RequestOptions;

  constructor(private http: Http, private authenticationService: AuthenticationService) {}



  /**
   *
   */
  public getAll(): Observable<RequestResponse<Group> | string> {
    this.setHeaders();

    return this.http.get(routes.groups(), this.options)
      .map((res: Response) => res.json() as RequestResponse<Group>)
      .catch(this.handleError);
  }

  public getOne(params: any): Observable<any> {
    return Observable.throw('');
  }

  /*
   *
   */
  public add(g: Group): Observable<RequestResponse<Group> | string> {
    this.setHeaders();
    const body = JSON.stringify(g);
    // console.log(body);

    return this.http.post(routes.groups(), body, this.options)
      .map((res: Response) => res.json() as RequestResponse<Group>)
      .catch(this.handleError);
  }

  /**
   *
   */
  public update(g: Group): Observable<RequestResponse<Group> | string> {
    this.setHeaders();

    const body = JSON.stringify(g);
    // console.log(body);

    return this.http.put(routes.group(g), body, this.options)
      .map((res: Response) => res.json() as RequestResponse<Group>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public delete(g: Group): Observable<RequestResponse<Group> | string> {
    this.setHeaders();

    return this.http.delete(routes.group(g), this.options)
      .map((res: Response) => res.json() as RequestResponse<Group>)
      .catch(this.handleError);
  }

  /**
   *
   */
  public getGroupMembers(g: Group): Observable<RequestResponse<User> | string> {
    this.setHeaders();

    return this.http.get(routes.groupMembers(g), this.options)
      .map((res: Response) => res.json() as RequestResponse<User>)
      .catch(this.handleError);
  }

  /**
   *
   */
  public updateGroupMembers(g: Group, u: Array<User>): Observable<RequestResponse<User> | string> {
    this.setHeaders();
    const body = JSON.stringify(u);

    return this.http.put(routes.groupMembers(g), body, this.options)
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

  /**
   *
   */
  private handleError(error: any) {
    const errMsg = (error.message) ? error.message : error.status ?
      `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    console.error(error);
    return Observable.throw(errMsg);
  }
}
