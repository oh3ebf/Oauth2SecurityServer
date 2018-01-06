/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: OauthClientToken class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 4.9.2017
 */
package oh3ebf.spring.security.oauth.user_interface.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "oauth_client_token")
public class OauthClientToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "token_id")
    private String tokenId;
    @Lob
    @Column(name = "token")
    private byte[] token;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "authentication_id")
    private String authenticationId;
    @Size(max = 255)
    @Column(name = "user_name")
    private String userName;
    @Size(max = 255)
    @Column(name = "client_id")
    private String clientId;

    public OauthClientToken() {
    }

    public OauthClientToken(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authenticationId != null ? authenticationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthClientToken)) {
            return false;
        }
        OauthClientToken other = (OauthClientToken) object;
        if ((this.authenticationId == null && other.authenticationId != null) || (this.authenticationId != null && !this.authenticationId.equals(other.authenticationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "oh3ebf.spring.security.oauth.user_interface.model.OauthClientToken[ authenticationId=" + authenticationId + " ]";
    }

}
