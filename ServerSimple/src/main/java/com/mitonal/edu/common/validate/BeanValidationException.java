package com.mitonal.edu.common.validate;

import com.mitonal.edu.common.enums.BusinessError;
import com.mitonal.edu.common.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class BeanValidationException extends BusinessException {

	private static final long serialVersionUID = 2328912752148709L;

	private BindingResult bindingResult;

	public BeanValidationException(String code, Object... parameters) {
		super(code, BusinessError.DATA_VALIDATION_FAILED, parameters);
	}

	public BeanValidationException(String code, BindingResult bindingResult, Object... parameters) {
		super(code, BusinessError.DATA_VALIDATION_FAILED, parameters);
		this.bindingResult = bindingResult;
	}

	public BeanValidationException(String code, Throwable throwable, BindingResult bindingResult,
			Object... parameters) {
		super(code, BusinessError.DATA_VALIDATION_FAILED, throwable, parameters);
		this.bindingResult = bindingResult;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	public String getErrorMessage() {
		if (this.getBindingResult().getErrorCount() > 0) {
			List<ObjectError> list = this.getBindingResult().getAllErrors();
			StringBuilder sb = new StringBuilder();
			for (ObjectError tmp : list) {
				if (tmp instanceof FieldError) {
					FieldError fieldError = (FieldError) tmp;
					sb.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("\n");
				}
			}
			return sb.toString();
		}
		return "参数错误";
	}

}