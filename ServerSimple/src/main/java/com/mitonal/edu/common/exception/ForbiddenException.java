package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.enums.BusinessError;

/**
 * 权限不允许
 */
public class ForbiddenException extends BusinessException {

	private static final long serialVersionUID = 23413335518709L;

	public ForbiddenException(String code, Object... parameters) {
		super(code, BusinessError.FORBIDDEN, parameters);
	}

	public ForbiddenException(String code, Throwable throwable, Object... parameters) {
		super(code, BusinessError.FORBIDDEN, throwable, parameters);
	}

}