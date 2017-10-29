/*
 * Software: 
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

    @Override
    public Users getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<Users> getAllUsers() {
        Iterable<Users> users = userRepository.findAll();
        return Utils.IterableToList(users);
    }

    @Override
    public Users getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void saveUser(Users user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}
