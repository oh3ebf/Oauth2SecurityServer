/**
 * Software: SpringOauth2Server
 * Module: RestAuthenticationEntryPoint class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 1.2.2017
 */

package oh3ebf.spring.security.oauth.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

@Service
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = Logger.getLogger(RestAuthenticationEntryPoint.class);

    /**
     * Function send REST compatible response to failed authentication request
     *
     * @param request to respond
     * @param response to put error
     * @param arg2 exception instance
     * @throws IOException on error
     * @throws ServletException on error
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
            throws IOException, ServletException {

        log.info("Sending REST Unauthorized");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized.");
    }
}
