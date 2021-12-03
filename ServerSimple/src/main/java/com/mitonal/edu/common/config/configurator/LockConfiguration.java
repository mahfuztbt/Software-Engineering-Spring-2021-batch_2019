package com.mitonal.edu.common.config.configurator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.ExpirableLockRegistry;

import javax.sql.DataSource;

@Configuration
public class LockConfiguration {

	@Bean
	@ConditionalOnMissingBean(LockRepository.class)
	public LockRepository lockRepository(DataSource dataSource) {
		DefaultLockRepository lockRepository = new DefaultLockRepository(dataSource);
		lockRepository.setPrefix("t_it_");
		return lockRepository;
	}

	@Bean
	@ConditionalOnMissingBean(ExpirableLockRegistry.class)
	public ExpirableLockRegistry jdbcLockRegistry(LockRepository lockRepository) {
		return new JdbcLockRegistry(lockRepository);
	}

	@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis")
	public static class RedisLockConfig {

		@Bean
		public ExpirableLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
			return new RedisLockRegistry(redisConnectionFactory, "spring-cloud");
		}

	}

}
