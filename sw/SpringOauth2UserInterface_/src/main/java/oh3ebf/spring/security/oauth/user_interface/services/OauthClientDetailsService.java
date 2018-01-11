/*
 * Software: SpringOauth2Server REST client for user interface
 * Module: GroupService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.services;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.OauthClientDetails;
import oh3ebf.spring.security.oauth.user_interface.repository.OauthClientDetailsRepository;
import oh3ebf.spring.security.oauth.user_interface.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDetailsService implements OauthClientDetailsServiceIF {

    @Autowired
    OauthClientDetailsRepository oauthClientDetailsRepository;

    /**
     * Function return all Oauth client details
     * 
     * @return client details
     */
    @Override
    public List<OauthClientDetails> getAllOauthClientDetails() {
        Iterable<OauthClientDetails> details = oauthClientDetailsRepository.findAll();
        return Utils.IterableToList(details);
    }

    /**
     * Function return specified Oauth client details
     * 
     * @param id of client
     * @return client details
     */
    @Override
    public OauthClientDetails getOauthClientDetailsById(Long id) {
        return oauthClientDetailsRepository.findOne(id);
    }

    /**
     * Function saves oauth client details
     * 
     * @param client to save
     * @return saved object
     */
    @Override
    public OauthClientDetails saveOauthClientDetails(OauthClientDetails client) {

        return oauthClientDetailsRepository.save(client);
    }

    /**
     * Function removes specified client details 
     * @param id of client details to remove
     */
    @Override
    public void deleteOauthClientDetails(Long id) {
        oauthClientDetailsRepository.delete(id);
    }

}
