package com.mitonal.edu.sys.controller;

import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.enums.BusinessError;
import com.mitonal.edu.common.exception.BusinessException;
import com.mitonal.edu.common.security.entites.*;
import com.mitonal.edu.common.security.service.AuthorizationManager;
import com.mitonal.edu.common.security.service.SessionStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 认证API
 */
@Api(tags = "平台-认证API")
@RestController
@RequestMapping(value = ApiPrefix.SYS_PREFIX)
@Slf4j
public class AuthController {

	//#region 字段

	@Resource
	private AuthorizationManager authorizationManager;

	@Resource
	private AuthenticationManager authenticationManager;

	@Resource
	private AuthenticationSuccessHandler successHandler;

	@Resource
	private AuthenticationFailureHandler failureHandler;

	@Resource
	private SessionStateService sessionHelper;

	@Resource
	private HttpServletRequest request;

	@Resource
	private HttpServletResponse response;

	//#endregion

	@ApiOperation("登录接口")
	@PostMapping("/auth-token")
	public void auth(@RequestBody @Valid PlatformLoginCommand command) throws IOException, ServletException {
		Realm realm = ObjectUtils.isEmpty(command.getRealm()) ? Realm.PLATFORM
				: Realm.valueOf(command.getRealm().toUpperCase());
		//
		switch (realm) {
		//
		case PLATFORM: {
			ClientId clientId = ObjectUtils.isEmpty(command.getClientId()) ? ClientId.BROWSER
					: ClientId.valueOf(command.getClientId().toUpperCase());
			switch (clientId) {
			case APP:
			case BROWSER: {
				auth(command.getUsername(), command.getPassword(), realm, clientId);
				break;
			}
			default:
				throw new BusinessException("不支持该客户端", BusinessError.UNAUTHORIZED);
			}
			break;
		}

		// todo: 暂时未使用,用于多资源服务器登录
		case RESOURCE_SERVER: {
			ClientId clientId = ClientId.SERVER;
			auth(command.getUsername(), command.getPassword(), realm, clientId);
			break;
		}
		default:
			throw new BusinessException("不支持该客户端", BusinessError.UNAUTHORIZED);
		}

	}

	@ApiOperation("退出接口")
	@DeleteMapping("/auth-token")
	public ResponseEntity<Void> unauth() {
		try {
			DefaultUserDetails userDetails = sessionHelper.getCurrentLoginUser();
			authorizationManager.logout(userDetails);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	private void auth(String username, String password, Realm realm, ClientId clientId)
			throws IOException, ServletException {
		LoginAuthenticationToken token = new LoginAuthenticationToken(username, password, realm, clientId);
		try {
			Authentication authentication = authenticationManager.authenticate(token);
			successHandler.onAuthenticationSuccess(request, response, authentication);
		}
		catch (AuthenticationException e) {
			failureHandler.onAuthenticationFailure(request, response, e);
		}
		catch (Exception e) {
			log.error("principal[{}] login fail, reason: ", token.getPrincipal(), e);
			response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		}
	}

	@ApiOperation("获取基础认证信息")
	@GetMapping("/principal")
	public DefaultUserDetails getPrincipal() {
		return sessionHelper.getCurrentLoginUser();
	}

}
