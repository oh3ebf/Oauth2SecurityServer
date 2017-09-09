/**
 * Software: SpringOauth2Server
 * Module: RestExceptionTranslationFilter class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 31.1.2017
 */

package oh3ebf.spring.security.oauth.server;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@Component
public class RestExceptionTranslationFilter extends ExceptionTranslationFilter {

    public RestExceptionTranslationFilter() {
        super(new RestAuthenticationEntryPoint());
    }

    public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    public RestExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint,
            RequestCache requestCache) {
        super(authenticationEntryPoint, requestCache);
    }

    @Override
    protected void sendStartAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, AuthenticationException reason)
            throws ServletException, IOException {

        super.sendStartAuthentication(req, resp, chain, reason);
    }
}
