/**
 * Software: SpringOauth2Server
 * Module: SecurityOauthServerMVCExceptionHanler class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 1.2.2017
 */

package oh3ebf.spring.security.oauth.server.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityOauthServerMVCExceptionHanler extends ResponseEntityExceptionHandler {

    /**
     * 
     * @param ex
     * @param request
     * @return 
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = "This should be application specific";

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handler for Jackson UnrecognizedPropertyException
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException.class})
    protected ResponseEntity<Object> handleUnrecognizedField(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getLocalizedMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handler for application specific exception
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {SecurityOauthServerOperationFailedException.class})
    protected ResponseEntity<Object> handleSurveyPollOperationFailed(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
