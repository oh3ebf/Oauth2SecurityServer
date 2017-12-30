/**
 * Software: SpringOauth2Server
 * Module: LimitLoginAuthenticationProvider class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 26.1.2017
 */

package oh3ebf.spring.security.oauth.server;

import java.util.Date;
import oh3ebf.spring.security.oauth.server.model.UserAttempts;
import oh3ebf.spring.security.oauth.server.repository.UserDetailsDaoIF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.apache.log4j.Logger;


@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

    private final Logger log = Logger.getLogger(LimitLoginAuthenticationProvider.class);

    @Autowired
    UserDetailsDaoIF userDetailsDao;
    
    @Autowired
    @Qualifier("userDetailsService")
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    /**
     * Function handles user login attempts counting and account locking
     *
     * @param authentication instance to verify
     * @return Authentication for user
     * @throws AuthenticationException on error
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            Authentication auth = super.authenticate(authentication);

            //reset the user_attempts if reach here, means login success, else exception will be thrown
            userDetailsDao.resetFailAttempts(authentication.getName());

            return auth;
        } catch (BadCredentialsException ex) {
            //invalid login, update to user_attempts
            userDetailsDao.updateFailAttempts(authentication.getName());
            log.info("Failed login attempt: " + authentication.getName());
            
            throw ex;
        } catch (LockedException ex) {
            //this user is locked!
            String error = "";
            UserAttempts userAttempts = userDetailsDao.getUserAttempts(authentication.getName());

            if (userAttempts != null) {
                Date lastAttempts = userAttempts.getLastModified();
                error = "User account is locked! <br><br>Username : " + authentication.getName() + "<br>Last Attempts : " + lastAttempts;
                log.info("Account locked: " + authentication.getName());
            } else {
                error = ex.getMessage();
            }

            throw new LockedException(error);
        }
    }
}
