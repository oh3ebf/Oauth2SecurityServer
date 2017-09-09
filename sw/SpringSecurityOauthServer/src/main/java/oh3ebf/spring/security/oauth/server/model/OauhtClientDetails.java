/**
 * Software: SpringOauth2Server
 * Module: OauhtClientDetails class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 25.1.2017
 */

package oh3ebf.spring.security.oauth.server.model;


public class OauhtClientDetails {
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedictUri;
    private String authorities;
    private int accessTokenValidity;
    private int refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the resourceIds
     */
    public String getResourceIds() {
        return resourceIds;
    }

    /**
     * @param resourceIds the resourceIds to set
     */
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    /**
     * @return the clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * @param clientSecret the clientSecret to set
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return the authorizedGrantTypes
     */
    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    /**
     * @param authorizedGrantTypes the authorizedGrantTypes to set
     */
    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    /**
     * @return the webServerRedictUri
     */
    public String getWebServerRedictUri() {
        return webServerRedictUri;
    }

    /**
     * @param webServerRedictUri the webServerRedictUri to set
     */
    public void setWebServerRedictUri(String webServerRedictUri) {
        this.webServerRedictUri = webServerRedictUri;
    }

    /**
     * @return the authorities
     */
    public String getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    /**
     * @return the accessTokenValidity
     */
    public int getAccessTokenValidity() {
        return accessTokenValidity;
    }

    /**
     * @param accessTokenValidity the accessTokenValidity to set
     */
    public void setAccessTokenValidity(int accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    /**
     * @return the refreshTokenValidity
     */
    public int getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    /**
     * @param refreshTokenValidity the refreshTokenValidity to set
     */
    public void setRefreshTokenValidity(int refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    /**
     * @return the additionalInformation
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * @param additionalInformation the additionalInformation to set
     */
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * @return the autoapprove
     */
    public String getAutoapprove() {
        return autoapprove;
    }

    /**
     * @param autoapprove the autoapprove to set
     */
    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }
    
}
