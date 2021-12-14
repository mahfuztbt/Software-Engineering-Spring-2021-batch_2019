package com.mitonal.edu.common.security.service;

import com.mitonal.edu.common.entities.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * session 状态服务
 */
@Component
public class SessionStateService {

	/**
	 * @return
	 */
	public DefaultUserDetails getCurrentLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (DefaultUserDetails) authentication.getPrincipal();
	}

	public Long getCurrentLoginUserId() {
		return Long.valueOf(getCurrentLoginUser().getId());
	}

}
