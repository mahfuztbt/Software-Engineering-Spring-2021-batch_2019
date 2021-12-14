package com.mitonal.edu.common.security.service.impl;

import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.entities.LoginUserDetails;
import com.mitonal.edu.common.security.entites.Realm;
import com.mitonal.edu.common.security.entites.RequestAuthenticationToken;
import com.mitonal.edu.common.service.iUserQueryService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserDetailsAuthenticationProiderImpl extends AbstractUserDetailsAuthenticationProvider {

	// @Resource
	private iUserQueryService userSvc;

	// 目前没有使用资源服务器
	// @Resource
	// private ResourceServerQueryService resourceServerQueryService;

	@Override
	protected DefaultUserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		RequestAuthenticationToken token = (RequestAuthenticationToken) authentication;
		DefaultUserDetails user;
		user = sampleRetrieve(username, authentication, token);

		return user;
	}

	/**
	 * 返回测试的零时用户
	 *
	 * @param username
	 * @param authenticatio
	 * @param token
	 */
	DefaultUserDetails sampleRetrieve(String username, UsernamePasswordAuthenticationToken authenticatio,
									  RequestAuthenticationToken token) {

		//
		var usInfo = DefaultUserDetails.builder().realm(Realm.PLATFORM)
			.clientId(token.getClientId())
			.id(UUID.randomUUID().toString()).username(username).password("password")
			.secret(token.getSecret())
			.build();

		return usInfo;
	}

	/**
	 * 返回测试的零时用户
	 *
	 * @param username
	 * @param authenticatio
	 * @param token
	 */
	DefaultUserDetails realRetrieve(String username, UsernamePasswordAuthenticationToken authenticatio,
									RequestAuthenticationToken token) {
		//	DefaultUserDetails user = null;

		//
		Long userId = Long.valueOf(token.getUserId());
		var platformUser = userSvc.findUserByIdNotNull(userId);

		//
		var usInfo = DefaultUserDetails.builder().id(token.getUserId()).username(platformUser.getUsername()).password("")
			.realm(Realm.PLATFORM).clientId(token.getClientId()).secret(token.getSecret())
			.enabled(platformUser.isEnabled()).credentialsNonExpired(platformUser.isCredentialsNonExpired())
			.accountNonExpired(platformUser.isAccountNonExpired())
			.accountNonLocked(platformUser.isAccountNonLocked()).build();

		return usInfo;

		// 目前没有使用资源服务器
		/*
		 * // switch (token.getRealm()) { case PLATFORM: { Long userId =
		 * Long.valueOf(token.getUserId()); UserPo platformUser =
		 * userQueryService.findUserByIdNotNull(userId); user =
		 * UserDetailsImpl.builder().id(token.getUserId()).username(platformUser.
		 * getUsername()).password("")
		 * .realm(Realm.PLATFORM).clientId(token.getClientId()).secret(token.getSecret())
		 * .enabled(platformUser.isEnabled()).credentialsNonExpired(platformUser.
		 * isCredentialsNonExpired())
		 * .accountNonExpired(platformUser.isAccountNonExpired())
		 * .accountNonLocked(platformUser.isAccountNonLocked()).build(); } case
		 * RESOURCE_SERVER: Long resourceServerId = Long.valueOf(token.getUserId());
		 * ResourceServerPo resource =
		 * resourceServerQueryService.findByIdNotNull(resourceServerId); user =
		 * UserDetailsImpl.builder().id(token.getUserId()).username(resource.getIdentity()
		 * ).password("")
		 * .realm(Realm.RESOURCE_SERVER).clientId(token.getClientId()).secret(token.
		 * getSecret())
		 * .enabled(resource.getStatus().equals(ResourceServerStatus.ENABLE.getId()))
		 * .credentialsNonExpired(true).accountNonExpired(true).accountNonLocked(true).
		 * build(); default: return null; }
		 */
		//	return user;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return RequestAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
												  UsernamePasswordAuthenticationToken authentication) {

	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
														 UserDetails user) {
		var tmp = (RequestAuthenticationToken) authentication;
		return new RequestAuthenticationToken(principal, "", tmp.getUserId(), tmp.getRealm(), tmp.getClientId(),
			tmp.getSecret());
	}

}
