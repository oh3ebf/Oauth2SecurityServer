/**
 * Software: SpringOauth2Server
 * Module: CustomUserDetailsManager class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 31.1.2017
 */

package oh3ebf.spring.security.oauth.server.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import oh3ebf.spring.security.oauth.server.model.CustomUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * https://github.com/spring-projects/spring-security/blob/master/core/src/main/java/org/springframework/security/provisioning/JdbcUserDetailsManager.java
 * class is used to modify database
 */
@Component
public class CustomUserDetailsManager extends JdbcUserDetailsManager {    

    private final Logger log = Logger.getLogger(CustomUserDetailsManager.class);

    private static final String SQL_ALL_USERS = "SELECT users.*, auth.authority FROM users, user_authorities AS auth WHERE users.id = auth.users_id";
    
    private static final String SQL_ALL_USERS_GROUP = "SELECT  u.username, u.password, g.authority AS authority FROM users AS u INNER JOIN (groups AS g, group_members AS gm) "
            + "ON (u.id = gm.users_id AND g.id = gm.groups_id)";

    private static final String SQL_INSERT_USERS = "INSERT INTO users (username, password, enabled, account_non_expired, account_non_locked, "
            + "credentials_non_expired, first_name, last_name, email, phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) where username = ?";

    private static final String SQL_UPDATE_USERS = "UPDATE users SET username = ?, password = ?, enabled = ?, account_non_expired = ?, "
            + "account_non_locked = ?, credentials_non_expired = ?, first_name = ?, last_name = ?, email = ?, phone = ? where username = ?";

    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";

    private static final String SQL_USER_BY_ID = "SELECT users.*, auth.authority FROM users, user_authorities AS auth "
            + "WHERE users.id = auth.users_id AND users.id = ?";
    
    private static final String SQL_GROUP_AUTHORITIES_BY_USER_NAME = "SELECT  u.username, u.password, g.authority AS authority FROM users AS u INNER JOIN (groups AS g, group_members AS gm) "
            + "ON (u.id = gm.users_id AND g.id = gm.groups_id) WHERE u.username = ?";
        
    private String sqlAllUsers;

    @Autowired
    Environment env;

    @Autowired
    private DataSource dataSource;

    public CustomUserDetailsManager() {
    }

    /**
     *
     */
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
        setUsersByUsernameQuery(SQL_ALL_USERS);
        setCreateUserSql(SQL_INSERT_USERS);
        setUpdateUserSql(SQL_UPDATE_USERS);

        setGroupAuthoritiesByUsernameQuery(SQL_GROUP_AUTHORITIES_BY_USER_NAME);
        setGroupAuthoritiesSql(DEF_GROUP_AUTHORITIES_QUERY_SQL);
        setRolePrefix(env.getProperty("login.role.prefix", String.class));

        boolean groupsSupport = env.getProperty("login.groups.support", boolean.class);
        if (groupsSupport) {
            // user groups enabled            
            sqlAllUsers = SQL_ALL_USERS_GROUP;
            setEnableGroups(groupsSupport);
            setEnableAuthorities(false);
            log.info("Group authorities support enabled.");
        } else {
            sqlAllUsers = SQL_ALL_USERS;
        }
    }

    /**
     * Function creates new user
     *
     * @param user to insert in to data base
     */
    @Override
    public void createUser(UserDetails user) {
        super.createUser(user);

        CustomUser u = (CustomUser) user;
        getJdbcTemplate().update(SQL_INSERT_USERS,
                new Object[]{
                    u.getUsername(),
                    u.getPassword(),
                    u.isEnabled(),
                    u.isAccountNonExpired(),
                    u.isAccountNonLocked(),
                    u.isCredentialsNonExpired(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getEmail(),
                    u.getPhone()
                });
    }

    /**
     * Function updates user data
     *
     * @param user to update in data base
     */
    @Override
    public void updateUser(UserDetails user) {
        super.updateUser(user);

        CustomUser u = (CustomUser) user;
        getJdbcTemplate().update(SQL_UPDATE_USERS,
                new Object[]{
                    u.getUsername(),
                    u.getPassword(),
                    u.isEnabled(),
                    u.isAccountNonExpired(),
                    u.isAccountNonLocked(),
                    u.isCredentialsNonExpired(),
                    u.getFirstName(),
                    u.getLastName(),
                    u.getEmail(),
                    u.getPhone()
                });
    }

    /**
     * Function removes user
     *
     * @param id of user to remove
     */
    public void deleteUser(long id) {
        getJdbcTemplate().update(SQL_DELETE_USER, new Object[]{id});
    }

    /**
     *
     * @return List of users
     */
    public List<CustomUser> getAllUsers() {
        return getJdbcTemplate().query(sqlAllUsers, new CustomUserMapper(this));
    }

    /**
     *
     * @param id of user
     * @return CustomUser
     */
    public CustomUser getUserById(long id) {
        return getJdbcTemplate().queryForObject(SQL_USER_BY_ID, new Object[]{id}, new CustomUserMapper(this));
    }

    private class CustomUserMapper implements RowMapper<CustomUser> {

        private CustomUserDetailsManager m;

        public CustomUserMapper() {

        }

        public CustomUserMapper(CustomUserDetailsManager m) {
            this.m = m;
        }

        /**
         * Function maps db fields to new user object
         *
         * @param rs ResultSet
         * @param rowNum N/A
         * @return CustomUser as mapped user data
         * @throws SQLException on error
         */
        @Override
        public CustomUser mapRow(ResultSet rs, int rowNum) throws SQLException {

            // load user authorities
            List<GrantedAuthority> authority = m.loadGroupAuthorities(rs.getString("username"));

            // populate default user object
            CustomUser user = new CustomUser(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getBoolean("enabled"),
                    rs.getBoolean("account_non_expired"),
                    rs.getBoolean("account_non_locked"),
                    rs.getBoolean("credentials_non_expired"),
                    authority
            );

            // set new fields
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));

            return user;
        }
    }
}
