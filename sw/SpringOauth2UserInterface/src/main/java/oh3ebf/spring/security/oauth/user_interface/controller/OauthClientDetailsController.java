/**
 * Software:
 * Module: OauthClientDetailsController class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 4.9.2017
 */
package oh3ebf.spring.security.oauth.user_interface.controller;

import oh3ebf.spring.security.oauth.user_interface.utils.StatusMessage;
import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.OauthClientDetails;
import oh3ebf.spring.security.oauth.user_interface.services.OauthClientDetailsService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthClientDetailsController {

    private final Logger log = Logger.getLogger(OauthClientDetailsController.class);
    @Autowired
    OauthClientDetailsService oauthClientDetailsService;

    /**
     * Function returns all details
     *
     * @return OauthClientDetails as list
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_USER')")
    @RequestMapping(value = "/oauth-client-details", method = GET, produces = "application/json")
    public StatusMessage getAllOauthClientDetails() {
        StatusMessage<OauthClientDetails> msg = new StatusMessage<>();

        // get details
        List<OauthClientDetails> details = oauthClientDetailsService.getAllOauthClientDetails();

        // check response
        if (details.isEmpty()) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            msg.setResponse(details);
        }

        return msg;
    }

    /**
     * Function adds new Oauth2 client details
     *
     * @param details
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/oauth-client-details", method = POST, produces = "application/json")
    public StatusMessage addOauthClientDetails(@RequestBody OauthClientDetails details) {

        oauthClientDetailsService.saveOauthClientDetails(details);
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function returns Oauth2 client details by id or name
     *
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_USER')")
    @RequestMapping(value = "/oauth-client-details/{query}", method = GET, produces = "application/json")
    public StatusMessage getOauthClientDetails(@PathVariable("query") String query) {
        StatusMessage<OauthClientDetails> msg = new StatusMessage<>();
        OauthClientDetails details = null;
        Long idNumber = null;

        try {
            idNumber = Long.parseLong(query);
        } catch (NumberFormatException ex) {
            log.info("User query not done by id number, try by name");
        }

        // select query type
        if (idNumber != null) {
            details = oauthClientDetailsService.getOauthClientDetailsById(idNumber);
        }

        // check response
        if (details == null) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            msg.getResponse().add(details);
        }

        return msg;
    }

    /**
     * Function updates existing Oauth2 client details
     *
     * @param details
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/oauth-client-details/{query}", method = POST, produces = "application/json")
    public StatusMessage updateOauthClientDetails(@RequestBody OauthClientDetails details, @PathVariable("query") String query) {

        oauthClientDetailsService.saveOauthClientDetails(details);

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function deletes Oauth2 client details by id
     *
     * @param id
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/oauth-client-details/{id}", method = DELETE, produces = "application/json")
    public StatusMessage deleteOauthClientDetails(@PathVariable("id") long id) {
        oauthClientDetailsService.deleteOauthClientDetails(id);

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

}
