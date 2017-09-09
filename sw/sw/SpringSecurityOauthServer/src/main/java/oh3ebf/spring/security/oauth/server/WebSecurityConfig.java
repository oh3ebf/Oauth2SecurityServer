/**
 * Software: SpringOauth2Server
 * Module: WebSecurityConfig class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 31.1.2017
 */

package oh3ebf.spring.security.oauth.server;


import oh3ebf.spring.security.oauth.server.repository.CustomUserDetailsManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger log = Logger.getLogger(WebSecurityConfig.class);
    @Autowired
    @Qualifier("authenticationProvider")
    AuthenticationProvider authenticationProvider;

    /**
     * Function configures user authentication 
     * 
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        // set LimitLoginAuthenticationProvider
        auth.authenticationProvider(authenticationProvider);
        // set own user details manager for LimitLoginAuthenticationProvider
        auth.userDetailsService(userDetailsManager());
    }

    /**
     * Function configures JDBC user details source
     * 
     * @return
     */
    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        // return own user details manager 
        JdbcUserDetailsManager manager = new CustomUserDetailsManager();

        return manager;
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Function configures web security features
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        log.info("Configure web security...");
        /*
        http.authorizeRequests().antMatchers("/**").authenticated()
                .and().httpBasic().realmName("OAuth Server");
         */

 /*
        http.csrf().disable();
        
        http.authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
         */
 /*
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll();
         */
        http.addFilterAfter(exceptionTranslationFilter(), ExceptionTranslationFilter.class);

        http.csrf().disable()
                .authorizeRequests()
                //.antMatchers("/users/**")
                //.permitAll()
                //.antMatchers("/groups/**")
                //.permitAll()
                .antMatchers("/login")
                .permitAll()
                .anyRequest()
                .authenticated()
        .and()
                .formLogin()
                .permitAll()
                ;
    }

    /**
     * Function returns new instance of REST exception translation filter
     * 
     * @return new exception translation filter
     */
    @Bean
    public static ExceptionTranslationFilter exceptionTranslationFilter() {
        RestExceptionTranslationFilter exceptionTranslationFilter = new RestExceptionTranslationFilter(new RestAuthenticationEntryPoint());
        //RestAccessDeniedHandler accessDeniedHandlerImpl = new RestAccessDeniedHandler();
        //exceptionTranslationFilter.setAccessDeniedHandler(accessDeniedHandlerImpl);
        exceptionTranslationFilter.afterPropertiesSet();
        return exceptionTranslationFilter;
    }
}
