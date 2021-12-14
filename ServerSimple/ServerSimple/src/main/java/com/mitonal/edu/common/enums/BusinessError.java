package com.mitonal.edu.common.enums;

import org.springframework.http.HttpStatus;

/**
 * 业务异常枚举类，若无必要无需增加
 */
public enum BusinessError {

	/**
	 * 客户端通用错误
	 */
	GENERAL_CLIENT("Bad Request", 40000000, HttpStatus.BAD_REQUEST),

	/**
	 * 尚有关联数据错误
	 */
	DATA_RELATED("Bad Request", 40000001, HttpStatus.BAD_REQUEST),

	/**
	 * 数据缺失错误
	 */
	DATA_NOT_FOUND("Bad Request", 40000002, HttpStatus.BAD_REQUEST),

	/**
	 * 数据库记录部分属性冲突错误
	 */
	DATA_DUPLICATE("Bad Request", 40000003, HttpStatus.BAD_REQUEST),

	/**
	 * 数据参数校验错误
	 */
	DATA_VALIDATION_FAILED("Bad Request", 40000004, HttpStatus.BAD_REQUEST),

	/**
	 * 登录错误
	 */
	UNAUTHORIZED("Unauthorized", 40100000, HttpStatus.UNAUTHORIZED),

	/**
	 * 权限错误
	 */
	FORBIDDEN("Forbidden", 40300000, HttpStatus.FORBIDDEN),
	/**
	 * 未定义错误
	 */
	UNDEFINED_ERROR("Internal Server Error", 50000000, HttpStatus.INTERNAL_SERVER_ERROR);

	/**
	 * 错误简述
	 */
	private String alias;

	/**
	 * 错误编号
	 */
	private long code;

	/**
	 * 错误状态码
	 */
	private HttpStatus status;

	BusinessError(String alias, long code, HttpStatus status) {
		this.alias = alias;
		this.code = code;
		this.status = status;
	}

	public long getCode() {
		return code;
	}

	public String getAlias() {
		return alias;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
