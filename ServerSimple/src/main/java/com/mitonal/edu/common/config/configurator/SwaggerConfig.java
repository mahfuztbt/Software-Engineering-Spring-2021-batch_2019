package com.mitonal.edu.common.config.configurator;

import com.fasterxml.classmate.TypeResolver;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.mitonal.edu.common.properties.AppInfo;
import com.mitonal.edu.common.properties.SecurityProperties;
import com.mitonal.edu.common.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;

import springfox.boot.starter.autoconfigure.SpringfoxConfigurationProperties;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * api文档, 帮助配置
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
// @EnableSwagger2WebMvc
@EnableConfigurationProperties({ SpringDataWebProperties.class, SwaggerProperties.class })
public class SwaggerConfig {

	@Resource
	private SpringDataWebProperties springDataWebProperties;

	@Resource
	private SpringfoxConfigurationProperties springfoxConfigurationProperties;

	@Resource
	private SecurityProperties securityProperties;

	@Resource
	private SwaggerProperties swaggerProperties;

	@Resource
	private AppInfo info;

	@Bean
	public Docket petApi() {
		// SWAGGER_2
		// OAS_30
		return new Docket(DocumentationType.SWAGGER_2).enable(swaggerProperties.getEnable()) // 是否启用Swagger
				.apiInfo(apiInfo()) // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
				.select() // 设置哪些接口暴露给Swagger展示
				// 设置需要扫描的api
				// .apis(RequestHandlerSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.mitonal.edu")) // 按包扫描api
				.paths(PathSelectors.any()).build()
				/* 设置安全模式，swagger可以设置访问token */
				.securitySchemes((apiKey())).securityContexts(securityContext());
		// .pathMapping(swaggerProperties.getUrlPrefix()); //修改默认doc文档路径
	}

	@Bean
	public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
		return new AlternateTypeRuleConvention() {

			@Override
			public int getOrder() {
				return Ordered.HIGHEST_PRECEDENCE;
			}

			@Override
			public List<AlternateTypeRule> rules() {
				return singletonList(newRule(resolver.resolve(Pageable.class), resolver.resolve(pageableMixin())));
			}
		};
	}

	/**
	 * 支持正常显示Pageable
	 * @return
	 */
	private Type pageableMixin() {
		return new AlternateTypeBuilder()
				.fullyQualifiedClassName(String.format("%s.generated.%s", Pageable.class.getPackage().getName(),
						Pageable.class.getSimpleName()))
				.withProperties(Stream
						.of(property(Integer.class, springDataWebProperties.getPageable().getPageParameter()),
								property(Integer.class, springDataWebProperties.getPageable().getSizeParameter()),
								property(String.class, springDataWebProperties.getSort().getSortParameter()))
						.collect(toList()))
				.build();
	}

	private AlternateTypePropertyBuilder property(Class<?> type, String name) {
		return new AlternateTypePropertyBuilder().withName(name).withType(type).withCanRead(true).withCanWrite(true);
	}

	/**
	 * 安全模式，这里指定token通过Authorization头请求头传递
	 */
	private List<SecurityScheme> apiKey() {
		return singletonList(new ApiKey("Authorization", "Authorization", "header"));
	}
	// private List<ApiKey> apiKey() {
	// return singletonList(new ApiKey("JWT", "Authorization", "header"));
	// }

	/**
	 * 安全上下文
	 */
	private List<SecurityContext> securityContext() {
		return singletonList(
				SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build());
	}

	/**
	 * 默认的安全上引用
	 */
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientId("swagger-client-id")
				.clientSecret("swagger-client-secret").realm("swagger-realm").appName("swagger-app").scopeSeparator(",")
				.additionalQueryStringParams(null).useBasicAuthenticationWithAccessCodeGrant(false).build();
	}

	/**
	 * 应用摘要信息
	 */
	private ApiInfo apiInfo() {
		// 用ApiInfoBuilder进行定制
		return new ApiInfoBuilder()
				// 设置标题
				.title(info.getName())
				// 描述
				.description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
				// 作者信息
				.contact(new Contact(info.getName(), null, null))
				// 版本
				.version("版本号:" + info.getVersion()).build();
	}

}
