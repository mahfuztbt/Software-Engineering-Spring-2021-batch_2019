package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.enums.BusinessError;

/**
 * 数据库唯一性约束被触发
 */
public class DulplicateKeyException extends BusinessException {

	private static final long serialVersionUID = 2348912758318709L;

	public DulplicateKeyException(String code, Object... parameters) {
		super(code, BusinessError.DATA_DUPLICATE, parameters);
	}

	public DulplicateKeyException(String code, Throwable throwable, Object... parameters) {
		super(code, BusinessError.DATA_DUPLICATE, throwable, parameters);
	}

}