package com.mitonal.edu.common.security.filter;

import com.mitonal.edu.common.entities.LoginUserDetails;
import com.mitonal.edu.common.event.LoginSuccessEvent;
import com.mitonal.edu.common.properties.SecurityProperties;
import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.security.properties.SecurityContants;
import com.mitonal.edu.common.security.service.AuthorizationManager;
import com.mitonal.edu.common.security.utils.JwtUtils;
import com.mitonal.edu.common.utils.IpUtil;
import com.mitonal.edu.common.utils.JsonHelper;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Resource
	private JwtUtils jwtUtils;

	@Resource
	private AuthorizationManager authorizationManager;

	@Resource
	private SecurityProperties properties;

	@Resource
	private ApplicationEventPublisher applicationEventPublisher;

	public AuthenticationSuccessHandlerImpl() {
	}

	public AuthenticationSuccessHandlerImpl(JwtUtils jwtUtils, AuthorizationManager authorizationManager,
			ApplicationEventPublisher applicationEventPublisher) {
		this.jwtUtils = jwtUtils;
		this.authorizationManager = authorizationManager;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@SneakyThrows
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		//Todo: 替换为真实登录
		realLogin(request,response,authentication);

		//sampleLogin(request, response, authentication);

	}

	/**
	 * 模拟登录
	 * @param request
	 * @param response
	 * @param authentication
	 * @throws IOException
	 * @throws ServletException
	 * @throws CloneNotSupportedException
	 */
	void sampleLogin(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException, CloneNotSupportedException {
		var timeout = properties.getDefaultTimeout();// ；、、.getOrDefault(user.getClientId(),
														// properties.getTimeout());

		//
		DefaultUserDetails usInfo = (DefaultUserDetails) authentication.getDetails();

		String secret = UUID.randomUUID().toString();
		usInfo.setSecret(secret);

		String jwtToken = jwtUtils.createUserToken(usInfo, secret, timeout);
		usInfo.setToken(jwtToken);

		//
		var user = new LoginUserDetails();
		user.setUsername(usInfo.getUsername());
		user.setId(usInfo.getId());
		user.setPassword(usInfo.getPassword());
		user.setSecret(usInfo.getSecret());
		user.setRealm(usInfo.getRealm());
		user.setClientId(usInfo.getClientId());
		//user.setAuthorities(usInfo.getAuthorities());
		user.setInfo(usInfo);
		user.setAccess_token(usInfo.getToken());
		usInfo.setToken(null);

		authorizationManager.login(user);

		response.setStatus(200);
		response.addHeader("content-type", MediaType.APPLICATION_JSON_VALUE);

		var simpleUser = (DefaultUserDetails) user.clone();
		simpleUser.setSecret("");

		var usJs = JsonHelper.toJSONString(simpleUser);

		//
		response.getWriter().write(usJs);
	}

	void realLogin(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		var user = (DefaultUserDetails) authentication.getDetails();
		String secret = UUID.randomUUID().toString();
		user.setSecret(secret);
		String jwtToken = jwtUtils.createUserToken(user, secret);
		authorizationManager.login(user);
		applicationEventPublisher.publishEvent(new LoginSuccessEvent(IpUtil.getIpAddr(request), user));
		response.getWriter().write(SecurityContants.TOKEN_PREFIX + jwtToken);
	}

}
