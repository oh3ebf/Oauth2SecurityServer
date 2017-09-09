/**
 * Software: SpringOauth2Server
 * Module: UserDetailsDao class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 31.1.2017
 */

package oh3ebf.spring.security.oauth.server.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import oh3ebf.spring.security.oauth.server.model.UserAttempts;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsDao extends JdbcDaoSupport implements UserDetailsDaoIF {

    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE USERS SET account_non_locked = ? WHERE username = ?";
    private static final String SQL_USERS_COUNT = "SELECT count(*) FROM USERS WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM user_attempts AS att, users AS usr WHERE usr.id=att.users_id AND usr.username = ?";
    private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO user_attempts (users_id, attempts, last_modified) "
            + "VALUES((SELECT id FROM users WHERE username=?),?,?)";

    private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE user_attempts as att, users as usr "
            + "SET att.attempts = att.attempts + 1, att.last_modified = ? "
            + "WHERE usr.id=att.users_id AND usr.username = ?";

    private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE user_attempts as att, users as usr "
            + "SET att.attempts = 0, att.last_modified = ? "
            + "WHERE usr.id=att.users_id AND usr.username = ?";

    private final Logger log = Logger.getLogger(UserDetailsDao.class);
    private int userMaxAttempts = 3;

    @Autowired
    private DataSource dataSource;

    @Autowired
    Environment env;

    public UserDetailsDao() {

    }

    /**
     *
     */
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);

        String userAttempParameter = env.getProperty("login.user.attempts.max");

        try {
            userMaxAttempts = Integer.parseInt(userAttempParameter);
        } catch (NumberFormatException ex) {
            log.error("Failed to parse integr from " + userAttempParameter);
        }

        log.info("User attempts set to " + userMaxAttempts + ", (default 3)");
    }

    /**
     *
     * @param username
     */
    @Override
    public void updateFailAttempts(String username) {

        if (userMaxAttempts != 0) {
            UserAttempts user = getUserAttempts(username);

            if (user == null) {
                if (isUserExists(username)) {
                    // if no record, insert a new
                    getJdbcTemplate().update(SQL_USER_ATTEMPTS_INSERT, new Object[]{username, 1, new Date()});
                }
            } else {

                if (isUserExists(username)) {
                    // update attempts count, +1
                    getJdbcTemplate().update(SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS, new Object[]{new Date(), username});
                }

                if (user.getAttempts() + 1 >= userMaxAttempts) {
                    // locked user
                    getJdbcTemplate().update(SQL_USERS_UPDATE_LOCKED, new Object[]{false, username});
                    log.info("User account " + username + " locked.");

                    // throw exception
                    throw new LockedException("User Account is locked!");
                }
            }
        } else {
            log.info("User account disabling not enable");
        }
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public UserAttempts getUserAttempts(String username) {
        try {
            UserAttempts userAttempts = getJdbcTemplate().queryForObject(SQL_USER_ATTEMPTS_GET,
                    new Object[]{username}, new RowMapper<UserAttempts>() {
                @Override
                public UserAttempts mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserAttempts user = new UserAttempts();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setAttempts(rs.getInt("attempts"));
                    user.setLastModified(rs.getDate("last_modified"));

                    return user;
                }
            });
            return userAttempts;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     *
     * @param username
     */
    @Override
    public void resetFailAttempts(String username) {
        getJdbcTemplate().update(SQL_USER_ATTEMPTS_RESET_ATTEMPTS, new Object[]{new Date(), username});
    }

    /**
     *
     * @param username
     * @return
     */
    private boolean isUserExists(String username) {
        boolean result = false;

        int count = getJdbcTemplate().queryForObject(SQL_USERS_COUNT, new Object[]{username}, Integer.class);
        if (count > 0) {
            result = true;
        }

        return result;
    }
}
