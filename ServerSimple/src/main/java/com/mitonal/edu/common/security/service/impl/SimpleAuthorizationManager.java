package com.mitonal.edu.common.security.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleAuthorizationManager extends AbstractAuthorizationManager {

	private final Map<String, Secret> map = new ConcurrentHashMap<>();

	@Override
	public void loginInner(String key, String value) {
		Secret secretItem = new Secret(value, System.currentTimeMillis() + properties.getDefaultTimeout());
		map.put(key, secretItem);
	}

	@Override
	public void logoutInner(String key) {
		map.remove(key);
	}

	private String getSecret(String key) {
		if (!map.containsKey(key)) {
			return null;
		}
		Secret secret = map.get(key);
		if (System.currentTimeMillis() > secret.getExpiredTime()) {
			map.remove(key);
			return null;
		}
		secret.setExpiredTime(System.currentTimeMillis() + properties.getDefaultTimeout());
		map.put(key, secret);
		return secret.getSecret();
	}

	@Override
	public boolean validateInner(String key, String secret) {
		return secret != null && secret.equals(getSecret(key));
	}

	@Data
	@AllArgsConstructor
	class Secret {

		private String secret;

		private long expiredTime;
	}

}
