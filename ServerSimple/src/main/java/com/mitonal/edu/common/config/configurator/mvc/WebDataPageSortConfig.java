package com.mitonal.edu.common.config.configurator.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

/**
 * 分页 , 排序配置
 */
// @Configuration
// @EnableSpringDataWebSupport
// @EnableConfigurationProperties(SpringDataWebProperties.class)
public class WebDataPageSortConfig {

	/*
	 * @Resource private SpringDataWebProperties properties;
	 *
	 * @Bean public PageableHandlerMethodArgumentResolverCustomizer
	 * pageableHandlerMethodArgumentResolverCustomizer() { return (resolver) -> {
	 * SpringDataWebProperties.Pageable pageable = this.properties.getPageable();
	 * resolver.setPageParameterName(pageable.getPageParameter());
	 * resolver.setSizeParameterName(pageable.getSizeParameter());
	 * resolver.setOneIndexedParameters(pageable.isOneIndexedParameters());
	 * resolver.setPrefix(pageable.getPrefix());
	 * resolver.setQualifierDelimiter(pageable.getQualifierDelimiter());
	 * resolver.setFallbackPageable( PageRequest.of(0, pageable.getDefaultPageSize()));
	 * resolver.setMaxPageSize(pageable.getMaxPageSize()); }; }
	 *
	 * @Bean public SortHandlerMethodArgumentResolverCustomizer
	 * sortHandlerMethodArgumentResolverCustomizer() { return (resolver) -> {
	 * resolver.setSortParameter(this.properties.getSort().getSortParameter()); }; }
	 */

}
