package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.entities.BaseResponse;
import com.mitonal.edu.common.enums.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 5)
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

	// @Autowired
	// private LanguageHelper languageHelper;

	@Autowired
	private HttpServletRequest request;

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<BaseResponse> process(AccessDeniedException exception) {
		BaseResponse baseResponse = BaseResponse.builder().error(BusinessError.FORBIDDEN.getAlias())
				.errorCode(BusinessError.FORBIDDEN.getCode()).status(HttpStatus.FORBIDDEN.value())
				.path(request.getServletPath()).message(exception.getMessage()).build();
		return ResponseEntity.status(baseResponse.getStatus()).body(baseResponse);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<BaseResponse> process(BusinessException exception) {
		return generateBaseResponse(exception);
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ResponseEntity<BaseResponse> process(AuthenticationException exception) {
		BusinessException result = new BusinessException(exception.getMessage(), BusinessError.UNAUTHORIZED);
		return generateBaseResponse(result);
	}

	private void loggerException(Throwable e) {
		if (log.isTraceEnabled()) {
			log.error("exception process", e);
		}
	}

	private ResponseEntity<BaseResponse> generateBaseResponse(BusinessException exception) {
		loggerException(exception);
		String message = exception.getMessage();
		// languageHelper .getMessage(exception.getMessage(), exception.getParameters());
		BaseResponse baseResponse = BaseResponse.builder().error(exception.getError().getAlias())
				.errorCode(exception.getError().getCode()).status(exception.getError().getStatus().value())
				.message(message).path(request.getServletPath()).build();
		return ResponseEntity.status(baseResponse.getStatus()).body(baseResponse);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		BaseResponse baseResponse = BaseResponse.builder().error(status.getReasonPhrase()).status(status.value())
				.message(ex.getMessage()).path(this.request.getServletPath()).build();
		return super.handleExceptionInternal(ex, baseResponse, headers, status, request);
	}

}
