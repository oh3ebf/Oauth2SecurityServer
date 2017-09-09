/**
 * Software: SpringOauth2Server
 * Module: SecurityOauthServerOperationFailedException class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 28.12.2016
 */
package oh3ebf.spring.security.oauth.server.exceptions;


public class SecurityOauthServerOperationFailedException extends Exception{
    private static final long serialVersionUID = 1997753363232807009L;

    public SecurityOauthServerOperationFailedException() {
    }

    public SecurityOauthServerOperationFailedException(String message) {
        super(message);
    }

    public SecurityOauthServerOperationFailedException(Throwable cause) {
        super(cause);
    }

    public SecurityOauthServerOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityOauthServerOperationFailedException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
}
}
