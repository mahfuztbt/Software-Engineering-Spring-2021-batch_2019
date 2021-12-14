package com.mitonal.edu.common.exception;

import com.mitonal.edu.common.enums.BusinessError;

/**
 * 通用的客户端参数错误
 */
public class ClientParamException extends BusinessException {

	private static final long serialVersionUID = 234133358318709L;

	public ClientParamException(String code, Object... parameters) {
		super(code, BusinessError.GENERAL_CLIENT, parameters);
	}

	public ClientParamException(String code, Throwable throwable, Object... parameters) {
		super(code, BusinessError.GENERAL_CLIENT, throwable, parameters);
	}

}