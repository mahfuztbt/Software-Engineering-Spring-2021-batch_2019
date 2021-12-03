package com.mitonal.edu.common.validate;

import com.mitonal.edu.common.entities.BaseResponse;
import com.mitonal.edu.common.exception.BusinessException;
import com.mitonal.edu.common.exception.ClientParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE - 10)
public class BeanValidateExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanValidateExceptionHandler.class);

	private static final Integer ARGUMENT_NOT_VALID_CODE = 100005;

	// @Autowired
	// private LanguageHelper languageHelper;

	@Autowired
	private HttpServletRequest request;

	@ExceptionHandler(BeanValidationException.class)
	public ResponseEntity<BaseResponse> process(BeanValidationException exception) {
		if (exception.getBindingResult().getErrorCount() > 0) {
			List<ObjectError> list = exception.getBindingResult().getAllErrors();
			StringBuilder sb = new StringBuilder();
			for (ObjectError tmp : list) {
				if (tmp instanceof FieldError) {
					FieldError fieldError = (FieldError) tmp;
					sb.append(fieldError.getField()).append(": ").append(exception.getMessage())
							// .append(languageHelper.getMessage(
							// fieldError.getDefaultMessage(),
							// fieldError.getArguments()))
							.append("\n");
				}
			}
			return generateBaseResponse(new ClientParamException(sb.toString()));
		}
		return generateBaseResponse(exception);
	}

	private void loggerException(Throwable e) {
		LOGGER.error("exception process", e);
	}

	private ResponseEntity<BaseResponse> generateBaseResponse(BusinessException exception) {
		String message = exception.getMessage();

		// languageHelper .getMessage(exception.getMessage(), exception.getParameters());
		BaseResponse baseResponse = BaseResponse.builder().error(exception.getError().getAlias())
				.errorCode(exception.getError().getCode()).status(exception.getError().getStatus().value())
				.message(message).path(request.getServletPath()).build();
		return ResponseEntity.status(baseResponse.getStatus()).body(baseResponse);
	}

}
