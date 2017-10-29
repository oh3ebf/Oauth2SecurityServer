/*
 * Software: Spring Mysql template
 * Module: UserServiceIF class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;

public interface GroupServiceIF {

    public List<Groups> getAllGroups();

    public Groups getGroupById(Long id);

    public Groups getGroupByName(String name);

    public void saveGroup(Groups user);

    public void deleteGroup(Long id);
}
