/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: Oauth2UserInterfaceMVCExceptionHanler class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 15.11.2017
 */

package oh3ebf.spring.security.oauth.user_interface.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Oauth2UserInterfaceMVCExceptionHanler extends ResponseEntityExceptionHandler {

    /**
     * Handler for IllegalArgumentException
     * 
     * @param ex exception
     * @param request to serve
     * @return response with error
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handler for Jackson UnrecognizedPropertyException
     *
     * @param ex exception 
     * @param request to serve
     * @return response with error
     */
    @ExceptionHandler(value = {com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException.class})
    protected ResponseEntity<Object> handleUnrecognizedField(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Handler for application specific exception
     *
     * @param ex exception
     * @param request to serve
     * @return response with error
     */
    @ExceptionHandler(value = {OperationFailedException.class})
    protected ResponseEntity<Object> handleOperationFailed(RuntimeException ex, WebRequest request) {

        String bodyOfResponse = ex.getMessage();

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
