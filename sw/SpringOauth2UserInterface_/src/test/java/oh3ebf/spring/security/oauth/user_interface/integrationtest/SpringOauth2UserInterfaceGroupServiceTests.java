/**
 * Software: SpringOauth2Server REST client for user interface
 * Module: SpringOauth2UserInterfaceGroupServiceTests class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 18.7.2017
 */
package oh3ebf.spring.security.oauth.user_interface.integrationtest;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.services.GroupService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("testing")
public class SpringOauth2UserInterfaceGroupServiceTests {

    private final Logger log = Logger.getLogger(SpringOauth2UserInterfaceGroupServiceTests.class);

    @Autowired
    GroupService service;

    @Test
    @Transactional
    public void foo() {
        List<Groups> g = service.getAllGroups();
        int a = 0;
    }
}
