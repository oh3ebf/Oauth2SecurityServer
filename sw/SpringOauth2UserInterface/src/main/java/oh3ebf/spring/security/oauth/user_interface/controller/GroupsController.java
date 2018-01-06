/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: GroupsController class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 20.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.controller;

import java.util.ArrayList;
import oh3ebf.spring.security.oauth.user_interface.utils.StatusMessage;
import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.model.Users;
import oh3ebf.spring.security.oauth.user_interface.services.GroupService;
import oh3ebf.spring.security.oauth.user_interface.utils.Utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GroupsController {

    private final Logger log = Logger.getLogger(GroupsController.class);
    @Autowired
    GroupService groupService;

    /**
     * Function returns all users
     *
     * @return users as list
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups", method = GET, produces = "application/json")
    public StatusMessage getAllGroups() {
        StatusMessage<Groups> msg = new StatusMessage<>();

        // get users
        List<Groups> groups = groupService.getAllGroups();

        // check response
        if (groups.isEmpty()) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            msg.setResponse(groups);
        }

        return msg;
    }

    /**
     * Function adds new group
     *
     * @param group
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups", method = POST, produces = "application/json")
    public StatusMessage addGroup(@RequestBody Groups group) {
        StatusMessage<Groups> msg = new StatusMessage<>();
        List<Groups> groups = new ArrayList<>();

        Groups g = groupService.saveGroup(group);
        groups.add(g);

        msg.setStatus(StatusMessage.MESSAGE_OK);
        msg.setResponse(groups);
        return msg;
    }

    /**
     * Function returns group by id or name
     *
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups/{query}", method = GET, produces = "application/json")
    public StatusMessage getGroup(@PathVariable("query") String query) {
        StatusMessage<Groups> msg = new StatusMessage<>();
        Groups group;
        Long idNumber = null;

        try {
            idNumber = Long.parseLong(query);
        } catch (NumberFormatException ex) {
            log.info("User query not done by id number, try by name");
        }

        // select query type
        if (idNumber != null) {
            group = groupService.getGroupById(idNumber);
        } else {
            // get by group name        
            group = groupService.getGroupByName(query);
        }

        // check response
        if (group == null) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            msg.getResponse().add(group);
        }

        return msg;
    }

    /**
     * Function updates existing group
     *
     * @param groupUpdated
     * @param idNumber
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups/{id}", method = PUT, produces = "application/json")
    public StatusMessage updateGroup(@RequestBody Groups groupUpdated, @PathVariable("id") long idNumber) {
        // get current data
        Groups group = groupService.getGroupById(idNumber);

        // update values
        group.setGroupName(groupUpdated.getGroupName());
        group.setDescription(groupUpdated.getDescription());
        group.setAuthority(groupUpdated.getAuthority());

        // store
        groupService.saveGroup(group);
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function deletes group by id
     *
     * @param id
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups/{id}", method = DELETE, produces = "application/json")
    public StatusMessage deleteGroup(@PathVariable("id") long id) {
        groupService.deleteGroup(id);

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function returns group members
     *
     * @param id
     * @return users as list
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups/{id}/members", method = GET, produces = "application/json")
    public StatusMessage getGroupMembers(@PathVariable("id") long id) {
        StatusMessage<Users> msg = new StatusMessage<>();

        // get users
        List<Users> users = groupService.getGroupUsers(id);
        Utils.clearPasswdFromUsers(users);

        // check response
        if (users.isEmpty()) {
            msg.setStatus(StatusMessage.MESSAGE_EMPTY);
        } else {
            msg.setStatus(StatusMessage.MESSAGE_OK);
            msg.setResponse(users);
        }

        return msg;
    }

    /**
     * Function updates group members
     *
     * @param idNumber
     * @param membersUpdated
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups/{id}/members", method = PUT, produces = "application/json")
    public StatusMessage updateGroupMembers(@PathVariable("id") long idNumber, @RequestBody List<Users> membersUpdated) {
        StatusMessage<Users> msg = new StatusMessage<>();

        // get current data
        Groups group = groupService.getGroupById(idNumber);

        // make working copy
        List<Users> tmp = new ArrayList<>();
        List<Users> users = groupService.getGroupUsers(idNumber);

        // users to remove        
        tmp.addAll(users);
        tmp.removeAll(membersUpdated);

        if (!tmp.isEmpty()) {
            for (Users u : tmp) {
                groupService.deleteUserFromGroup(u, group);
            }
        }

        // users to add
        tmp.clear();
        tmp.addAll(membersUpdated);
        tmp.removeAll(users);
        if (!tmp.isEmpty()) {
            for (Users u : tmp) {
                groupService.addUserToGroup(u, group);
            }
        }

        msg.setStatus(StatusMessage.MESSAGE_OK);

        return msg;
    }
}
