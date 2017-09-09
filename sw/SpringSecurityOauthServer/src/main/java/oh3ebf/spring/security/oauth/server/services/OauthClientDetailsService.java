/**
 * Software: SpringOauth2Server
 * Module: OauthClientDetailsService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 26.1.2017
 */

package oh3ebf.spring.security.oauth.server.services;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class OauthClientDetailsService implements ServiceInterface<ClientDetails> {    

    @Autowired
    DataSource dataSource;
    private JdbcClientDetailsService client;

    public OauthClientDetailsService() {

    }
//TODO mihin t�t� luokkaa k�ytet��n
    
    @PostConstruct
    private void initialize() {
        client = new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void create(ClientDetails p) {
        client.addClientDetails(p);
    }

    @Override
    public void delete(ClientDetails p) {
        client.removeClientDetails(p.getClientId());
    }

    @Override
    public void update(ClientDetails p) {
        client.updateClientDetails(p);
    }

    @Override
    public List<ClientDetails> listAll() {
        return client.listClientDetails();
    }

    @Override
    public ClientDetails getById(String id) {
        return client.loadClientByClientId(id);
    }

}
