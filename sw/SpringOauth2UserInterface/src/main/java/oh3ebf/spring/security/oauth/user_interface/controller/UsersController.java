/**
 * Software:
 * Module: UsersController class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 13.9.2017
 */
package oh3ebf.spring.security.oauth.user_interface.controller;

import oh3ebf.spring.security.oauth.user_interface.utils.StatusMessage;
import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Users;
import oh3ebf.spring.security.oauth.user_interface.services.GroupService;
import oh3ebf.spring.security.oauth.user_interface.services.UserServiceIF;

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
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_USER')")
    @RequestMapping(value = "/users", method = GET, produces = "application/json")
    public StatusMessage getAllUsers() {
        StatusMessage<Users> msg = new StatusMessage<>();

        // get users
        List<Users> users = userService.getAllUsers();

        // check response
        if (users.isEmpty()) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            for (Users u : users) {
                // do not send password to client
                //u.setPassword("");
            }

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
    @RequestMapping(value = "/users", method = POST, produces = "application/json")
    public StatusMessage addUser(@RequestBody Users user) {

        userService.saveUser(user);
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function returns user by id or name
     *
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and (hasRole('ROLE_OAUTH2_USER') or hasRole('ROLE_OAUTH2_ADMIN'))")
    @RequestMapping(value = "/users/{query}", method = GET, produces = "application/json")
    public StatusMessage getUser(@PathVariable("query") String query) {
        StatusMessage<Users> msg = new StatusMessage<>();
        Users user;
        Long idNumber = null;

        try {
            idNumber = Long.parseLong(query);
        } catch (NumberFormatException ex) {
            log.info("User query not done by id number, try by name");
        }

        // select query type
        if (idNumber != null) {
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
     * Function deletes user by id
     *
     * @param id
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users/{id}", method = DELETE, produces = "application/json")
    public StatusMessage deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     *
     * @param user
     * @param group
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users/{user}/group/{group}", method = POST, produces = "application/json")
    public StatusMessage addUserToGroup(@PathVariable("user") String user, @PathVariable("group") String group) {

        /*
        hae ryhmä
        hae käyttäjä
        tee liitos
         */
        //       usersAndGroupsService.addUserToGroup(user, group);
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     *
     * @param user
     * @param group
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/users/{user}/group/{group}", method = DELETE, produces = "application/json")
    public StatusMessage deleteUserFromGroup(@PathVariable("user") String user, @PathVariable("group") String group) {

        //       usersAndGroupsService.deleteUserFromGroup(user, group);
        //osaa hakea nimen... miten lisätään käytäjälle ryhmä???
        //String a = users.get(3).getGroupMembers().get(0).getGroupsId().getGroupName();
        /*for (Users u : users) {
            groupService.deleteUserFromGroup(u);
        }*/
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }
}
