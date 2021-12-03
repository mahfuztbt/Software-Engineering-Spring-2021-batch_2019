package com.mitonal.edu.common.config.configurator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * cache 配置
 */
@Configuration
@Slf4j
public class CacheConfig {

	@ConditionalOnProperty(value = "spring.cache.type", havingValue = "redis", matchIfMissing = true)
	@Configuration
	@ConditionalOnClass(RedisTemplate.class)
	public static class RedisCacheConfig extends CachingConfigurerSupport {

		@Resource
		private JacksonProperties jacksonProperties;

		@Resource
		private RedisConnectionFactory redisConnectionFactory;

		@Bean
		public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
			Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
					ObjectMapper.DefaultTyping.NON_FINAL);
			serializer.setObjectMapper(objectMapper);

			RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
			redisTemplate.setConnectionFactory(redisConnectionFactory);
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.setValueSerializer(serializer);
			redisTemplate.setHashKeySerializer(new StringRedisSerializer());
			redisTemplate.setHashValueSerializer(serializer);
			redisTemplate.afterPropertiesSet();

			return redisTemplate;
		}

		@Bean
		public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate) {
			RedisCacheWriter redisCacheWriter = RedisCacheWriter
					.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
			RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
					.prefixCacheNameWith("cache:").entryTtl(Duration.ofSeconds(10))
					.serializeValuesWith(RedisSerializationContext.SerializationPair
							.fromSerializer(redisTemplate.getValueSerializer()));
			return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
		}

	}

}