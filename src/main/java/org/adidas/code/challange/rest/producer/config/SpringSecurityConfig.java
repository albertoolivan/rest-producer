package org.adidas.code.challange.rest.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * https://www.mkyong.com/spring-boot/spring-rest-spring-security-example/
 * 
 * @author Alberto
 *
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.security.rest.user}")
	private String user;
	
	@Value("${spring.security.rest.password}")
	private String password;

	// create USER role
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(user).password(password).roles("USER");
    }

    // Secure the endpoints with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/city/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/city-distance/**").hasRole("USER")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
    
}