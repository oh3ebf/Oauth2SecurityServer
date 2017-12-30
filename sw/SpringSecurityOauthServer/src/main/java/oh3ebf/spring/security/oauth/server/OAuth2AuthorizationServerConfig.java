/**
 * Software: SpringOauth2Server
 * Module: OAuth2AuthorizationServerConfig class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 31.1.2017
 */

package oh3ebf.spring.security.oauth.server;

import java.util.Arrays;
import javax.annotation.PostConstruct;

import javax.sql.DataSource;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final Logger log = Logger.getLogger(OAuth2AuthorizationServerConfig.class);

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;
    private  TokenStore tokenStore;
    
    @PostConstruct
    private void initialize() {
        tokenStore = new JdbcTokenStore(dataSource);
    }
    /**
     *
     * @param oauthServer to configure
     * @throws Exception on error
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        // configure for basic authentication
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    /**
     * Function configures Oauth2 client services
     * 
     * @param clients to configure
     * @throws Exception on error
     */
    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * Function configures Spring authorization services end points
     * 
     * @param endpoints to configure
     * @throws Exception on error
     */
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        log.info("Configuring authorization end points...");
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer()));

        endpoints.tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager);
    }

    /**
     * Function configures token services
     * 
     * @return token services
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        log.info("Configuring token services...");
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setTokenStore(tokenStore);
        defaultTokenServices.setSupportRefreshToken(true);

        return defaultTokenServices;
    }

    /**
     * Function sets data source for authentication
     * 
     * @param dataSource to set
     * @return data source initializer
     */
    
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        log.info("Configuring datasource...");
        
        // JDBC token store configuration
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        return initializer;
    }
}
