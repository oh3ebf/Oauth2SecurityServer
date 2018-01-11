/*
 * Software: SpringOauth2Server REST client for user interface
 * Module: UserService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 14.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Users;
import oh3ebf.spring.security.oauth.user_interface.repository.UserRepository;
import oh3ebf.spring.security.oauth.user_interface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceIF {

    @Autowired
    private UserRepository userRepository;

    /**
     * Function return all users
     * 
     * @return users as list
     */
    @Override
    public List<Users> getAllUsers() {
        Iterable<Users> users = userRepository.findAll();

        return Utils.IterableToList(users);
    }

    /**
     * Function return user by id
     * 
     * @param id of user
     * @return user
     */
    @Override
    public Users getUserById(Long id) {
        return userRepository.findOne(id);
    }

    /**
     * Function return user by name
     * 
     * @param name of user
     * @return user
     */
    @Override
    public Users getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    /**
     * Function save user data
     * 
     * @param user to store
     * @return stored user object
     */
    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    /**
     * Function removes given user
     * 
     * @param id of user to remove
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
