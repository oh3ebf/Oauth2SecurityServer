/**
 * Software: SpringOauth2Server
 * Module: UserDetailsDaoIF class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 23.1.2017
 */
package oh3ebf.spring.security.oauth.server.repository;

import oh3ebf.spring.security.oauth.server.model.UserAttempts;

public interface UserDetailsDaoIF {

    void updateFailAttempts(String username);

    void resetFailAttempts(String username);

    UserAttempts getUserAttempts(String username);
}
