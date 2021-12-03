package com.mitonal.edu.common.security.config;

import com.mitonal.edu.common.security.service.AuthorizationManager;

import com.mitonal.edu.common.security.service.impl.RedisAuthorizationManager;
import com.mitonal.edu.common.security.service.impl.SimpleAuthorizationManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AuthorizationManagerConfig {

	/**
	 * 简单登录管理器，
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(AuthorizationManager.class)
	public AuthorizationManager simpleAuthorizationManager() {
		return new SimpleAuthorizationManager();
	}

	/**
	 * 通过 Redis 的常规管理器
	 */
	@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis", matchIfMissing = true)
	@ConditionalOnClass(StringRedisTemplate.class)
	public static class RedisAuthorizationManagerConfig {

		@Bean
		public AuthorizationManager redisAuthorizationManager() {
			return new RedisAuthorizationManager();
		}

	}

}
