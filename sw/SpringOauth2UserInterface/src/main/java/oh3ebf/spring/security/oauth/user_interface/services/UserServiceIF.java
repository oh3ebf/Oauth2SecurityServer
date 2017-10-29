/*
 * Software: Spring Mysql template
 * Module: UserServiceIF class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 14.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Users;

public interface UserServiceIF {

    public List<Users> getAllUsers();

    public Users getUserById(Long id);

    public Users getUserByName(String name);

    public void saveUser(Users user);

    public void deleteUser(Long id);
}
