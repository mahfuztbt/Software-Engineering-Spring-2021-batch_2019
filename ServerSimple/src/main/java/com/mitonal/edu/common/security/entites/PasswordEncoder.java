package com.mitonal.edu.common.security.entites;

public enum PasswordEncoder {

	/**
	 * @see org.springframework.security.crypto.password.PasswordEncoder
	 */
	BCRYPT("bcrypt"), PBKDF2("pbkdf2"), SCRYPT("scrypt"), SHA256("sha256"), MD5("md5"), NOOP("noop"),;

	private String code;

	PasswordEncoder(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}