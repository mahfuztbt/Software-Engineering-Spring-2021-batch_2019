package com.mitonal.edu.common.security.config;

import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.common.properties.SecurityProperties;
import com.mitonal.edu.common.security.filter.AuthenticationFailureHandlerImpl;
import com.mitonal.edu.common.security.filter.AuthenticationSuccessHandlerImpl;
import com.mitonal.edu.common.security.filter.JwtAuthenticationFilter;
import com.mitonal.edu.common.security.service.AuthorizationManager;
import com.mitonal.edu.common.security.utils.JwtUtils;
import de.codecentric.boot.admin.client.config.ClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableConfigurationProperties({ SecurityProperties.class }) // ,
																// FrontShopProperties.class,
																// YthOaProperties.class
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private String adminContextPath = "";

	@Resource
	private AuthorizationManager authorizationManager;

	@Resource
	private SecurityProperties properties;

	@Bean
	public JwtUtils jwtUtils() {
		JwtUtils utils = new JwtUtils();
		utils.setProperties(properties);
		return utils;
	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationManager.class)
	public AuthenticationManager providerManager(List<AuthenticationProvider> providers) {
		return new ProviderManager(providers);
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(
			ApplicationEventPublisher applicationEventPublisher) {
		// return new AuthenticationSuccessHandlerImpl();
		return new AuthenticationSuccessHandlerImpl(jwtUtils(), authorizationManager, applicationEventPublisher);
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandlerImpl();
	}

	/*
	 * //监控服务器
	 *
	 * @Configuration
	 *
	 * @Order(1) public static class ItManageAdapter extends WebSecurityConfigurerAdapter
	 * {
	 *
	 * private final AdminServerProperties adminServer;
	 *
	 * public ItManageAdapter(AdminServerProperties adminServerProperties,
	 * ClientProperties clientProperties) { this.adminServer = adminServerProperties; }
	 *
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * SavedRequestAwareAuthenticationSuccessHandler successHandler = new
	 * SavedRequestAwareAuthenticationSuccessHandler();
	 * successHandler.setTargetUrlParameter("redirectTo");
	 * successHandler.setDefaultTargetUrl(this.adminServer.path("/"));
	 *
	 * http.antMatcher(this.adminServer.path("/**")).headers().frameOptions().disable().
	 * and() .authorizeRequests((authorizeRequests) -> authorizeRequests
	 * .antMatchers(this.adminServer.path("/assets/**")).permitAll()
	 * .antMatchers(this.adminServer.path("/login")).permitAll().anyRequest().
	 * authenticated()) .formLogin((formLogin) ->
	 * formLogin.loginPage(this.adminServer.path("/login"))
	 * .successHandler(successHandler).and()) .logout((logout) ->
	 * logout.logoutUrl(this.adminServer.path("/logout")))
	 * .httpBasic(Customizer.withDefaults()) .csrf((csrf) ->
	 * csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	 * .ignoringRequestMatchers( new
	 * AntPathRequestMatcher(this.adminServer.path("/instances"),
	 * HttpMethod.POST.toString()), new
	 * AntPathRequestMatcher(this.adminServer.path("/instances/*"),
	 * HttpMethod.DELETE.toString()), new
	 * AntPathRequestMatcher(this.adminServer.path("/actuator/**")))) .rememberMe(
	 * (rememberMe) ->
	 * rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600)); }
	 *
	 * }
	 */

	/**
	 * 普通web应用
	 */
	@Configuration
	@Order(2)
	public static class ActuatorAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/actuator/**").anonymous().disable().
				authorizeRequests()
				.mvcMatchers("/**").
				authenticated()
					.and().httpBasic();
		}

	}

	/**
	 * 监控客户端
	 */
	@Configuration
	@Order(3)
	public static class GeneralAdapter extends WebSecurityConfigurerAdapter {

		@Bean
		public JwtAuthenticationFilter jwtAuthenticationFilter() {
			return new JwtAuthenticationFilter();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.headers().frameOptions().disable();
			// @formatter:off
            http.authorizeRequests()
                    .antMatchers(
                            ApiPrefix.Auth_API,
                            "/archives/api/v1/archives/oa")
                    .permitAll()
                    .antMatchers(
                                                      "/cms/**")
                    .authenticated()
                    .and()
                    .csrf()
                    .disable()
                    .cors()
                    .and()
                    .sessionManagement()
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class);
            // @formatter:on
		}

	}
	//
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.csrf().disable().cors().and()
	// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	// .and()
	// .addFilterBefore(jwtAuthenticationFilter(),
	// AnonymousAuthenticationFilter.class).anonymous()
	// .and()
	// .authorizeRequests().antMatchers("/api/v1/platform/auth-token",
	// "/error").permitAll()
	// .anyRequest().authenticated();
	//
	// }

	// /**
	// * 被spring security忽略的api
	// *
	// * @param web
	// * @throws Exception
	// */
	// @Override
	// public void configure(WebSecurity web) throws Exception {
	// web.ignoring().antMatchers(
	// "/favicon.ico",
	// "/v2/api-docs",
	// "/v2/api-docs/**",
	// "/v2/api-docs-ext**",
	// "/v2/api-docs-ext?**",
	// "/v2/api-docs-ext/**",
	// "/doc.html",
	// "/configuration/ui",
	// "/swagger-resources/**",
	// "/configuration/security",
	// "/swagger-ui.html",
	// "/webjars/**",
	// "/actuator/**"
	// );
	// }

}
