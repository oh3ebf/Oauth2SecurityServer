/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: OperationFailedException class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 28.12.2016
 */
package oh3ebf.spring.security.oauth.user_interface.exceptions;

public class OperationFailedException extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(Throwable cause) {
        super(cause);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationFailedException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
