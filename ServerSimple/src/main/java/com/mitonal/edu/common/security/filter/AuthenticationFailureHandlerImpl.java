package com.mitonal.edu.common.security.filter;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

	// @Resource
	// private LanguageHelper languageHelper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// response.sendError(HttpStatus.UNAUTHORIZED.value(),
		// languageHelper.getMessage(exception.getMessage()));
		response.sendError(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
	}

}
