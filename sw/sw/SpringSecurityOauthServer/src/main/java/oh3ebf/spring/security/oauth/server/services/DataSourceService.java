/**
 * Software: SpringOauth2Server
 * Module: DataSourceService class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 11.7.2017
 */

package oh3ebf.spring.security.oauth.server.services;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class DataSourceService {

    private final Logger log = Logger.getLogger(DataSourceService.class);
    @Autowired
    private Environment env;

    /**
     * Function connect to JDBC data source
     *
     * @return
     */
    @Bean
    public DataSource getDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        log.info("Connecting to datasource " + dataSource.getUrl() + "...");

        try {
            dataSource.getConnection().setAutoCommit(env.getProperty("jdbc.autocommit", boolean.class));
        } catch (SQLException ex) {
            log.error("Failed to get database connection:" + dataSource.getUrl() + "as user " + dataSource.getUsername());
        }

        return dataSource;
    }
}
