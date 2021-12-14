package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.enums.BusinessError;

/**
 * 数据关联的条件下，使用了不应该使用的操作
 */
public class DataRelatedException extends BusinessException {

	private static final long serialVersionUID = 2328912758348709L;

	public DataRelatedException(String code, Object... parameters) {
		super(code, BusinessError.DATA_RELATED, parameters);
	}

	public DataRelatedException(String code, Throwable throwable, Object... parameters) {
		super(code, BusinessError.DATA_RELATED, throwable, parameters);
	}

}
