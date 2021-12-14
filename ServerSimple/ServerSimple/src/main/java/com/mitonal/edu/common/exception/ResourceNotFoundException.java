package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.enums.BusinessError;

public class ResourceNotFoundException extends BusinessException {

	private static final long serialVersionUID = 2348912758348609L;

	public ResourceNotFoundException(String code, Object... parameters) {
		super(code, BusinessError.DATA_NOT_FOUND, parameters);
	}

	public ResourceNotFoundException(String code, Throwable throwable, Object... parameters) {
		super(code, BusinessError.DATA_NOT_FOUND, throwable, parameters);
	}

}
