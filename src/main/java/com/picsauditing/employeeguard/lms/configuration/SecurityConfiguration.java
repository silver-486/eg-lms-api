package com.picsauditing.employeeguard.lms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The implementation for security configuration
 *
 * @author: sergey.emelianov
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("ssouser@pics.com").password("123456").roles("USER");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
   	http.authorizeRequests()
				.antMatchers("/logout").access("permitAll")
				.antMatchers("/**").access("hasRole('ROLE_USER')")
				.and().formLogin()
				.and().logout();

	}

}
