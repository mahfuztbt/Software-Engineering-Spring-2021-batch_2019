package com.mitonal.edu.common.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public class RedisAuthorizationManager extends AbstractAuthorizationManager {

	@Autowired
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void loginInner(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value, properties.getDefaultTimeout(), TimeUnit.MILLISECONDS);
	}

	@Override
	public void logoutInner(String key) {
		if (stringRedisTemplate.hasKey(key)) {
			stringRedisTemplate.delete(key);
		}
	}

	private String getSecret(String key) {
		String secret = stringRedisTemplate.opsForValue().get(key);
		stringRedisTemplate.expire(key, properties.getDefaultTimeout(), TimeUnit.MILLISECONDS);
		return secret;
	}

	@Override
	public boolean validateInner(String key, String value) {
		return value != null && value.equals(getSecret(key));
	}

}
