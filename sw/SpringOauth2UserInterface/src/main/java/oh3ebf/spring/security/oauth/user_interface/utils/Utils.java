/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: Utils class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.utils;

import java.util.ArrayList;
import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Users;

public class Utils {

    /**
     * Function converts Iterable to List
     *
     * @param <E>
     * @param iterable
     * @return
     */
    public static <E> List<E> IterableToList(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<>();
        if (iterable != null) {
            for (E e : iterable) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * Function clear password from given user
     * 
     * @param user 
     */
    public static void clearPasswdFromUser(Users user) {
        // do not send password to client
        user.setPassword("");
    }

    /**
     * Function clear password from given user list
     * 
     * @param users 
     */
    public static void clearPasswdFromUsers(List<Users> users) {
        for (Users u : users) {
            // do not send password to client
            u.setPassword("");
        }
    }
}
