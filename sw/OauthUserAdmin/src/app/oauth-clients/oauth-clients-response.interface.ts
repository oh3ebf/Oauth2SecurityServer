import {OauthClientDetails} from '../oauth-clients/oauth-client-details';

export interface OauthClientDetailsResponse {
  status: string;
  response: OauthClientDetails[];
}
