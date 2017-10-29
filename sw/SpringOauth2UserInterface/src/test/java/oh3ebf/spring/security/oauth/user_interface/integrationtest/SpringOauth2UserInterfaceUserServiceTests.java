package oh3ebf.spring.security.oauth.user_interface.integrationtest;

import java.util.List;
import oh3ebf.spring.security.oauth.user_interface.model.Groups;
import oh3ebf.spring.security.oauth.user_interface.model.Users;
import oh3ebf.spring.security.oauth.user_interface.services.UserService;
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
public class SpringOauth2UserInterfaceUserServiceTests {

    private final Logger log = Logger.getLogger(SpringOauth2UserInterfaceUserServiceTests.class);

    @Autowired
    UserService service;

    @Test
    @Transactional
    public void bar() {
        List<Users> g = service.getAllUsers();
        
        Users u = service.getUserByName("oauthuser");
        List<Groups> d = u.getGroups();
        Users j = service.getUserById(10L);
        int a = 0;
    }
}
