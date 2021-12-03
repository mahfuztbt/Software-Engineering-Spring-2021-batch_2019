package com.mitonal.edu.common.security.service.impl;

import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.entities.LoginUserDetails;
import com.mitonal.edu.common.security.entites.LoginAuthenticationToken;
import com.mitonal.edu.common.security.entites.Realm;
import com.mitonal.edu.common.service.iUserQueryService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class AuthorizationProviderImpl extends AbstractUserDetailsAuthenticationProvider {

	@Resource
	private iUserQueryService userQueryService;

	// @Resource
	// private ResourceServerQueryService resourceServerQueryService;

	@Resource
	private PasswordEncoder passwordEncoder;

	@Override
	protected DefaultUserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		DefaultUserDetails us = null;

		//Todo: 替换为真实登录
		 us = realLogin(username, authentication);

		//us = sampleLogin(username, authentication);

		return us;
	}

	DefaultUserDetails realLogin(String username, UsernamePasswordAuthenticationToken authentication) {

		LoginAuthenticationToken token = (LoginAuthenticationToken) authentication;
		String credentials = authentication.getCredentials().toString();

		var user = userQueryService.findUserByUsername(username);
		if (user == null) {
			throw new BadCredentialsException("error.platform_user.login.password");
		}
		if (!passwordEncoder.matches(credentials, user.getPassword())) {
			throw new BadCredentialsException("error.platform_user.login.password");
		}
		return DefaultUserDetails.builder().id(user.getId().toString()).username(user.getUsername()).password("")
			.realm(Realm.PLATFORM).clientId(((LoginAuthenticationToken) authentication).getClientId())
			.enabled(user.isEnabled()).accountNonLocked(user.isAccountNonLocked())
			.accountNonExpired(user.isAccountNonExpired()).credentialsNonExpired(user.isCredentialsNonExpired())
			.build();
	}

	DefaultUserDetails sampleLogin(String username, UsernamePasswordAuthenticationToken authentication) {


		var usInfo = DefaultUserDetails.builder().id(UUID.randomUUID().toString())
			.username(username).password("")
			.realm(Realm.PLATFORM).clientId(((LoginAuthenticationToken) authentication)
				.getClientId()).build();

		return usInfo;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return LoginAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
												  UsernamePasswordAuthenticationToken authentication) {

	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
														 UserDetails user) {
		LoginAuthenticationToken result = (LoginAuthenticationToken) authentication;
		result.setDetails(user);
		return result;
	}

}
