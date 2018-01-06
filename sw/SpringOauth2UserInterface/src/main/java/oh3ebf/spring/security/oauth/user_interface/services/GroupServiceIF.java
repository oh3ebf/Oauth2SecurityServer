/*
 * Software: SpringOauth2Server REST client for user interface
 * Module: UserServiceIF class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;

import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.model.Users;

public interface GroupServiceIF {

    public List<Groups> getAllGroups();

    public Groups getGroupById(Long id);

    public Groups getGroupByName(String name);

    public List<Users> getGroupUsers(Long id);

    public Groups saveGroup(Groups user);

    public void deleteGroup(Long id);

    public void addUserToGroup(Users user, Groups group);

    public void deleteUserFromGroup(Users user, Groups group);
}
