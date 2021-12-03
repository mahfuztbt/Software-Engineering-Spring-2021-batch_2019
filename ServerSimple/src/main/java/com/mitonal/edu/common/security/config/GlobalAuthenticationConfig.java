package com.mitonal.edu.common.security.config;

import com.mitonal.edu.common.security.service.impl.AuthorizationProviderImpl;
import com.mitonal.edu.common.security.service.impl.UserDetailsAuthenticationProiderImpl;
import de.codecentric.boot.admin.client.config.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
public class GlobalAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

	@Resource
	private AuthorizationProviderImpl authorizationProvider;

	@Resource
	private UserDetailsAuthenticationProiderImpl userDetailsAuthenticationProider;

	@Resource
	private SecurityProperties securityProperties;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	public void init(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		User user = new User(
				// clientProperties.getUsername(),clientProperties.getPassword()
				securityProperties.getUser().getName(), "{noop}" + securityProperties.getUser().getPassword(),
				Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		inMemoryUserDetailsManager.createUser(user);

		//
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(inMemoryUserDetailsManager);
		authenticationManagerBuilder.authenticationProvider(authorizationProvider)
				.authenticationProvider(userDetailsAuthenticationProider)
				.authenticationProvider(daoAuthenticationProvider);
	}

}
