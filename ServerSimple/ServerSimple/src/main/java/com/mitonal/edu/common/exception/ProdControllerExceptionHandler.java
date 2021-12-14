package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.entities.BaseResponse;
import com.mitonal.edu.common.enums.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Order
@Profile("pro")
@Slf4j
public class ProdControllerExceptionHandler {

	// @Autowired
	// private LanguageHelper languageHelper;

	@Autowired
	private HttpServletRequest request;

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ResponseEntity<BaseResponse> process(Throwable exception) {
		loggerException(exception);
		String message = "系统发生意料外的异常"; // languageHelper.getMessage("system.error");
		BaseResponse baseResponse = BaseResponse.builder().error(BusinessError.UNDEFINED_ERROR.getAlias())
				.errorCode(BusinessError.UNDEFINED_ERROR.getCode()).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.path(request.getServletPath()).message(message).build();
		return ResponseEntity.status(baseResponse.getStatus()).body(baseResponse);
	}

	private void loggerException(Throwable e) {
		log.error("exception process", e);
	}

}
