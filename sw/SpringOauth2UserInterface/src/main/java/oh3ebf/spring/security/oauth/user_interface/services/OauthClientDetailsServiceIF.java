/*
 * Software: Spring Mysql template
 * Module: UserServiceIF class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.OauthClientDetails;

public interface OauthClientDetailsServiceIF {

    public List<OauthClientDetails> getAllOauthClientDetails();

    public OauthClientDetails getOauthClientDetailsById(Long id);

    public void saveOauthClientDetails(OauthClientDetails details);

    public void deleteOauthClientDetails(Long id);
}
