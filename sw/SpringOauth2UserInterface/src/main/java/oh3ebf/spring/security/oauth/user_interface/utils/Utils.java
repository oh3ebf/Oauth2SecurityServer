/**
 * Software:
 * Module: Utils class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.utils;

import java.util.ArrayList;
import java.util.List;

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
}
