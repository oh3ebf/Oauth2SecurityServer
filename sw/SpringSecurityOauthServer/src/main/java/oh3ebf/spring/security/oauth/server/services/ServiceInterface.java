/**
 * Software: SpringOauth2Server
 * Module: ServiceInterface class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 26.1.2016
 */
package oh3ebf.spring.security.oauth.server.services;

import java.util.List;

public interface ServiceInterface<T> {

    void create(T p);

    void delete(T p);

    void update(T p);

    List<T> listAll();

    T getById(String id);
}
