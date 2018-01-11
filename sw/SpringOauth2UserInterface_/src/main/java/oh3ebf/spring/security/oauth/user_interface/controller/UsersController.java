/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: UsersController class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 13.9.2017
 */
package oh3ebf.spring.security.oauth.user_interface.controller;

import java.util.ArrayList;
import oh3ebf.spring.security.oauth.user_interface.utils.StatusMessage;
import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Users;
import oh3ebf.spring.security.oauth.user_interface.services.GroupService;
import oh3ebf.spring.security.oauth.user_interface.services.UserServiceIF;
import oh3ebf.spring.security.oauth.user_interface.utils.Utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1")
public class UsersController {

    private final Logger log = Logger.getLogger(UsersController.class);
    @Autowired
    UserServiceIF userService;

    @Autowired
    GroupService groupService;

    /**
     * Function returns all users
     *
     * @return users as list
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusMessage getAllUsers() {
        StatusMessage<Users> msg = new StatusMessage<>();

        // get users
        List<Users> users = userService.getAllUsers();

        // check response
        if (users.isEmpty()) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            Utils.clearPasswdFromUsers(users);

            msg.setResponse(users);
        }

        return msg;
    }

    /**
     * Function adds new user
     *
     * @param user
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users", method = POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusMessage addUser(@RequestBody Users user) {
        StatusMessage<Users> msg = new StatusMessage<>();
        List<Users> users = new ArrayList<>();

        // save data and send response, do not send password to client
        Users u = userService.saveUser(user);
        Utils.clearPasswdFromUser(user);

        users.add(u);
        msg.setStatus(StatusMessage.MESSAGE_OK);
        msg.setResponse(users);

        return msg;
    }

    /**
     * Function returns user by id or name
     *
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and (hasRole('ROLE_OAUTH2_USER') or hasRole('ROLE_OAUTH2_ADMIN'))")
    @RequestMapping(value = "/users/{query}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusMessage getUser(@PathVariable("query") String query) {
        StatusMessage<Users> msg = new StatusMessage<>();
        Users user;
        Long idNumber = -1L;

        try {
            idNumber = Long.parseLong(query);
        } catch (NumberFormatException ex) {
            log.info("User query not done by id number, try by name");
        }

        // select query type
        if (idNumber > -1) {
            user = userService.getUserById(idNumber);
        } else {
            // get by user name        
            user = userService.getUserByName(query);
        }

        // check response
        if (user == null) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            // do not send password to client
            user.setPassword("");
            msg.getResponse().add(user);
        }

        return msg;
    }

    /**
     * Function updates user
     *
     * @param idNumber
     * @param userUpdated
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users/{id}", method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusMessage uppdateUser(@PathVariable("id") long idNumber, @RequestBody Users userUpdated) {

        // get current data
        Users user = userService.getUserById(idNumber);

        // update values
        user.setUsername(userUpdated.getUsername());
        user.setFirstName(userUpdated.getFirstName());
        user.setLastName(userUpdated.getLastName());
        user.setEmail(userUpdated.getEmail());
        user.setPhone(userUpdated.getPhone());
        user.setAccountNonExpired(userUpdated.getAccountNonExpired());
        user.setAccountNonLocked(userUpdated.getAccountNonLocked());
        user.setCredentialsNonExpired(userUpdated.getCredentialsNonExpired());
        user.setEnabled(userUpdated.getEnabled());

        // store
        userService.saveUser(user);
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function updates user
     *
     * @param idNumber
     * @param userUpdated
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and (hasRole('ROLE_OAUTH2_USER') or hasRole('ROLE_OAUTH2_ADMIN'))")
    @RequestMapping(value = "/users/{id}/password", method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusMessage uppdateUserPasswd(@PathVariable("id") long idNumber, @RequestBody Users userUpdated) {

        if (userUpdated.getPassword() == null || userUpdated.getPassword().isEmpty()) {
            throw new IllegalArgumentException("TXT_MISSING_PARAMETER");
        }

        // get current data
        Users user = userService.getUserById(idNumber);
        user.setPassword(userUpdated.getPassword());

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function deletes user by id
     *
     * @param id
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users/{id}", method = DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusMessage deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }
}
