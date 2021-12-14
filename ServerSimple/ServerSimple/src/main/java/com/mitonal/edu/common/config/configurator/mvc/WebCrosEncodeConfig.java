package com.mitonal.edu.common.config.configurator.mvc;

import com.mitonal.edu.common.properties.SecurityProperties;
import com.mitonal.edu.common.utils.resolver.MultiRequestBodyArgumentResolver;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基本跨域, 编码配置
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class WebCrosEncodeConfig implements WebMvcConfigurer {

	@Resource
	private SecurityProperties securityProperties;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// 添加MultiRequestBody参数解析器
		argumentResolvers.add(new MultiRequestBodyArgumentResolver());
	}

	// @Bean
	// public HttpMessageConverter<String> responseBodyConverter() {
	// // 解决中文乱码问题
	// return new StringHttpMessageConverter(StandardCharsets.UTF_8);
	// }

	// @Override
	// public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	// converters.add(responseBodyConverter());
	// }

	/**
	 * 跨域配置
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		securityProperties.getCors().forEach(properties -> {
			String[] allowedOrigins = new String[properties.getAllowedOrigins().size()];
			properties.getAllowedOrigins().toArray(allowedOrigins);
			String[] allowedMethods = new String[properties.getAllowedMethods().size()];
			properties.getAllowedMethods().stream().map(Enum::name).collect(Collectors.toList())
					.toArray(allowedMethods);
			String[] allowedHeaders = new String[properties.getAllowedHeaders().size()];
			properties.getAllowedHeaders().toArray(allowedHeaders);
			registry.addMapping(properties.getPath()).allowedOriginPatterns(allowedOrigins)
					.allowedMethods(allowedMethods).allowedHeaders(allowedHeaders)
					.allowCredentials(properties.getAllowCredentials());
		});
	}

}
