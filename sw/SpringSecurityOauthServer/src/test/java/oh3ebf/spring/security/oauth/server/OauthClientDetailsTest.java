package oh3ebf.spring.security.oauth.server;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import oh3ebf.spring.security.oauth.server.services.OauthClientDetailsService;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("testing")
public class OauthClientDetailsTest {

    private final Logger log = Logger.getLogger(OauthClientDetailsTest.class);

    @Autowired
    OauthClientDetailsService service;

    /**
     * Test case for document create and delete
     */
    @Test
    public void oauthClientDetailsTest() {
        log.info("Create and delete test");

        BaseClientDetails details = new BaseClientDetails();
        details.setClientId("test");
        service.create(details);

        assertTrue(service.listAll().size() > 0);

        Collection<String> authorities = new LinkedHashSet<>();
        authorities.add("test");
        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList(authorities.toArray(new String[authorities.size()]));

        details.setAuthorities(authoritiesList);
        service.update(details);

        assertEquals(service.getById("test").getAuthorities(), authoritiesList);

        service.delete(details);

    }
}
