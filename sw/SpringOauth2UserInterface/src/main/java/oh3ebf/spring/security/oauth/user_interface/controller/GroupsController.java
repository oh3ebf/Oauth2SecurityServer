/**
 * Software:
 * Module: GroupsController class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 20.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.controller;

import oh3ebf.spring.security.oauth.user_interface.utils.StatusMessage;
import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.services.GroupService;

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
public class GroupsController {

    private final Logger log = Logger.getLogger(GroupsController.class);
    @Autowired
    GroupService groupService;

    /**
     * Function returns all groups
     *
     * @return users as list
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_USER')")
    @RequestMapping(value = "/groups", method = GET, produces = "application/json")
    public StatusMessage getAllGroups() {
        StatusMessage<Groups> msg = new StatusMessage<>();

        // get groups
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
        if (group.getId() != null) {
        } else {
            // create new user

        }

        groupService.saveGroup(group);
        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function returns user by id or name
     *
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('read') and hasRole('ROLE_OAUTH2_USER')")
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
     * @param group
     * @param query
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('oauth2') and #oauth2.hasScope('write') and hasRole('ROLE_OAUTH2_ADMIN')")
    @RequestMapping(value = "/groups/{query}", method = POST, produces = "application/json")
    public StatusMessage updateGroup(@RequestBody Groups group, @PathVariable("query") String query) {

        groupService.saveGroup(group);

        return new StatusMessage(StatusMessage.MESSAGE_OK);
    }

    /**
     * Function deletes user by id
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

}
