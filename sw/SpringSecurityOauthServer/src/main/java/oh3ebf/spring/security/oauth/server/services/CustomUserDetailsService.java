/**
 * Software: SpringOauth2Server
 * Module: CustomUserDetailsService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 1.2.2017
 */

package oh3ebf.spring.security.oauth.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

/**
 * OAuth2 uses this file to user authentication
 *
 */
@Service("userDetailsService")
@Configuration
@PropertySource("classpath:application-default.properties")
public class CustomUserDetailsService extends JdbcDaoImpl {

    private final Logger log = Logger.getLogger(CustomUserDetailsService.class);
    public final static String SQL_All_USERS = "SELECT * FROM users WHERE username = ?";

    public final static String SQL_AUTHORITIES_BY_USER_NAME = "SELECT users.username, auth.authority FROM user_authorities AS auth, users WHERE users.id=auth.users_id AND users.username = ?";

    public final static String SQL_GROUP_AUTHORITIES_BY_USER_NAME = "SELECT  u.username, u.password, g.authority AS authority FROM users AS u INNER JOIN (groups AS g, group_members AS gm) "
            + "ON (u.id = gm.users_id AND g.id = gm.groups_id) WHERE u.username = ?";

    @Autowired
    Environment env;

    @Autowired
    private DataSource dataSource;

    public CustomUserDetailsService() {

    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        setUsersByUsernameQuery(SQL_All_USERS);
        setAuthoritiesByUsernameQuery(SQL_AUTHORITIES_BY_USER_NAME);
        setGroupAuthoritiesByUsernameQuery(SQL_GROUP_AUTHORITIES_BY_USER_NAME);
        setRolePrefix(env.getProperty("login.role.prefix", String.class));

        boolean groupsSupport = env.getProperty("login.groups.support", boolean.class);
        if (groupsSupport) {
            // user groups enabled
            setEnableGroups(true);
            setEnableAuthorities(false);

            log.info("Group authorities support enabled.");
        }
    }
    
    /**
     * Function return user details
     * 
     * @param username to query for
     * @return List on user details
     */
    @Override
    public List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(super.getUsersByUsernameQuery(), new String[]{username}, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean enabled = rs.getBoolean("enabled");
                boolean accountNonExpired = rs.getBoolean("account_non_expired");
                boolean credentialsNonExpired = rs.getBoolean("credentials_non_expired");
                boolean accountNonLocked = rs.getBoolean("account_non_locked");

                return new User(username, password, enabled, accountNonExpired, credentialsNonExpired,
                        accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
            }
        });
    }

    
    /**
     * Function creates new UserDeatails object from parameters
     * 
     * @param username to use
     * @param userFromUserQuery UserDetails from query
     * @param combinedAuthorities of user
     * @return new UserDetails object
     */
    @Override
    public UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        if (super.isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(),
                userFromUserQuery.isAccountNonLocked(), combinedAuthorities);
    }
}
