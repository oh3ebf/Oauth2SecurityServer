import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Observable';

import {OauthClientDetails} from '../oauth-clients/oauth-client-details';
import {Credentials} from '../core/authentication/credentials.interface';
import {AuthenticationService} from '../core/authentication/authentication.service';
import {ServiceActionInterfase} from '../shared/service-action.interface';
import {RequestResponse} from '../shared/request-response.interface';
import {environment} from '../../environments/environment';

const routes = {
  oauthClients: () => `${environment.tokenUrl}oauthadmin/api/v1/oauth-client-details`,
  oauthClient: (c: OauthClientDetails) => `${environment.tokenUrl}oauthadmin/api/v1/oauth-client-details/${c.id}`,
};

@Injectable()
export class OauthClientDetailsService implements ServiceActionInterfase {
  private headers: Headers;
  private options: RequestOptions;

  constructor(private http: Http, private authenticationService: AuthenticationService) {}

  /**
   *
   */
  public getAll(): Observable< RequestResponse<OauthClientDetails> | string> {
    this.setHeaders();

    return this.http.get(routes.oauthClients(), this.options)
      .map((res: Response) => res.json() as RequestResponse<OauthClientDetails>)
      .catch(this.handleError);
  }

  /**
   *
   */
  public getOne(c: OauthClientDetails): Observable< RequestResponse<OauthClientDetails> | string> {
    this.setHeaders();

    return this.http.get(routes.oauthClient(c), this.options)
      .map((res: Response) => res.json() as RequestResponse<OauthClientDetails>)
      .catch(this.handleError);
  }

  /**
   *
   */
  public add(c: OauthClientDetails): Observable<RequestResponse<OauthClientDetails> | string> {
    this.setHeaders();
    const body = JSON.stringify(c);

    return this.http.post(routes.oauthClients(), body, this.options)
      .map((res: Response) => res.json() as RequestResponse<OauthClientDetails>)
      .catch(this.handleError);
  }

  /**
   *
   */
  public update(c: OauthClientDetails): Observable<RequestResponse<OauthClientDetails> | string> {
    this.setHeaders();
    const body = JSON.stringify(c);

    return this.http.put(routes.oauthClient(c), body, this.options)
      .map((res: Response) => res.json() as RequestResponse<OauthClientDetails>)
      .catch(this.handleError);
  }

  /*
   *
   */
  public delete(c: OauthClientDetails): Observable<RequestResponse<OauthClientDetails> | string> {
    this.setHeaders();

    return this.http.delete(routes.oauthClient(c), this.options)
      .map((res: Response) => res.json() as Observable<RequestResponse<OauthClientDetails>>)
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
    const errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    console.error(error);
    return Observable.throw(errMsg);
  }
}
