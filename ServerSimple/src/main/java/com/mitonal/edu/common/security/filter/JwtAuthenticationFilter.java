package com.mitonal.edu.common.security.filter;

import com.mitonal.edu.common.properties.SecurityProperties;
import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.security.entites.RequestAuthenticationToken;
import com.mitonal.edu.common.security.properties.SecurityContants;
import com.mitonal.edu.common.security.service.AuthorizationManager;
import com.mitonal.edu.common.security.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Resource
	private JwtUtils jwtUtils;

	@Resource
	private AuthorizationManager authorizationManager;

	@Resource
	private SecurityProperties securityProperties;

	/**
	 *
	 */
	private Set<String> whites = new HashSet<String>() {
		{
			add("/yth/api/v1/auth-token");
			add("/oauth/api/v1/auth-token");
			add("/shop/api/v1/merchant-auth/auth-token");
		}
	};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authToken = getAuthToken(request);
		if (!StringUtils.hasText(authToken)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 检查是否设置过忽略校验的白名单
		boolean isWhiteRequest = securityProperties.getIgnoreTokenUrls().stream().anyMatch(pattern -> {
			AntPathRequestMatcher getRequestMatcher = new AntPathRequestMatcher(pattern.getPath(),
					pattern.getMethod().name());
			return getRequestMatcher.matches(request);
		});
		if (isWhiteRequest) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Map<String, Object> map = jwtUtils.parseToken(authToken);
			String secret = map.getOrDefault(JwtUtils.KEY_RAND, null).toString();
			DefaultUserDetails userDetails = jwtUtils.parseUser(map);
			String realm = userDetails.getRealm().name();
			String clientId = userDetails.getClientId().name();
			final String userId = userDetails.getId();

			// 验证失败
			if (!authorizationManager.validate(userDetails)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "the user is not logged in");
				log.trace("the user[userId={},realm={},clientId={}] is not logged in", userId, realm, clientId);
				return;
			}

			// 验证成功,设置token
			RequestAuthenticationToken token = new RequestAuthenticationToken(userDetails.getUsername(), "", userId,
					userDetails.getRealm(), userDetails.getClientId(), secret);
			token.setDetails(userDetails);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		catch (Throwable e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
			log.trace("The token is not valid, reason: ", e);
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 获取当前请求中附带的认证信息
	 * @param request
	 * @return
	 */
	private String getAuthToken(HttpServletRequest request) {
		String authToken = null;
		String authorization = request.getHeader(SecurityContants.HEADER_STRING);
		if (StringUtils.hasText(authorization) && authorization.startsWith(SecurityContants.TOKEN_PREFIX)) {
			authToken = authorization.substring(7);
		}
		return authToken;
	}

}
