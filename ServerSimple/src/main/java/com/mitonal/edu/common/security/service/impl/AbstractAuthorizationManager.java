package com.mitonal.edu.common.security.service.impl;

import com.mitonal.edu.common.properties.SecurityProperties;
import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.security.service.AuthorizationManager;

import javax.annotation.Resource;

/**
 *
 */
public abstract class AbstractAuthorizationManager implements AuthorizationManager {

	protected static final String STORE_NAME = "login";

	@Resource
	protected SecurityProperties properties;

	@Override
	public void login(DefaultUserDetails userDetails) {
		String realm = userDetails.getRealm().name();
		String clientId = userDetails.getClientId().name();
		String userId = userDetails.getId();
		String secret = userDetails.getSecret();
		loginInner(generateKey(realm, clientId, userId, secret), secret);

	}

	/**
	 * 实际登录方法
	 * @param key 唯一用户标识
	 * @param value 用户标识对应的随机密钥
	 */
	protected abstract void loginInner(String key, String value);

	@Override
	public boolean validate(DefaultUserDetails userDetails) {
		String realm = userDetails.getRealm().name();
		String clientId = userDetails.getClientId().name();
		String userId = userDetails.getId();
		String secret = userDetails.getSecret();
		return validateInner(generateKey(realm, clientId, userId, secret), secret);
	}

	@Override
	public void logout(DefaultUserDetails userDetails) {
		String realm = userDetails.getRealm().name();
		String clientId = userDetails.getClientId().name();
		String userId = userDetails.getId();
		String secret = userDetails.getSecret();
		logoutInner(generateKey(realm, clientId, userId, secret));
	}

	/**
	 * 实际退出方法
	 * @param key 唯一用户标识
	 */
	protected abstract void logoutInner(String key);

	/**
	 * 验证
	 * @param key 唯一用户标识
	 * @param secret 密钥
	 * @return 是否登录
	 */
	protected abstract boolean validateInner(String key, String secret);

	final String generateKey(String realm, String clientId, String userId, String secret) {
		if (properties.getShareLogin()) {
			return String.format("%s:%s:%s:%s:%s", STORE_NAME, realm, clientId, userId, secret);
		}
		else {
			return String.format("%s:%s:%s:%s", STORE_NAME, realm, clientId, userId);
		}
	}

}
