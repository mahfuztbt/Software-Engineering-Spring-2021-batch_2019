package com.mitonal.edu.common.properties;

import com.mitonal.edu.common.config.ApiPrefix;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "spin.security")
public class SecurityProperties {

	/**
	 * 是否处于 session 模式
	 */
	private boolean sessionManage = false;

	private String platformAuthUrl = ApiPrefix.Oauth_PREFIX + ApiPrefix.AUTH_Public_POSTFIX;

	private List<CorsProperties> cors = Collections.singletonList(new CorsProperties());

	private long defaultTimeout = 1000 * 60 * 60 * 2;

	private String secretKey = "!@Q4324zG$$=NGH#@(4982gR*&H!#(#(Q";

	private Boolean shareLogin = false;

	private List<IgnorePath> ignoreTokenUrls = new LinkedList<>();

	/*
	 * public List<CorsProperties> getCors() { return cors; }
	 *
	 * public void setCors(List<CorsProperties> cors) { this.cors = cors; }
	 *
	 * public long getDefaultTimeout() { return defaultTimeout; }
	 *
	 * public void setDefaultTimeout(long defaultTimeout) { this.defaultTimeout =
	 * defaultTimeout; }
	 *
	 * public String getSecretKey() { return secretKey; }
	 *
	 * public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
	 *
	 * public List<IgnorePath> getIgnoreTokenUrls() { return ignoreTokenUrls; }
	 *
	 * public void setIgnoreTokenUrls(List<IgnorePath> ignoreTokenUrls) {
	 * this.ignoreTokenUrls = ignoreTokenUrls; }
	 *
	 * public Boolean getShareLogin() { return shareLogin; }
	 *
	 * public void setShareLogin(Boolean shareLogin) { this.shareLogin = shareLogin; }
	 */

	/**
	 *
	 */
	@Data
	public static class CorsProperties {

		private String path = "/**";

		private List<String> allowedOrigins = Collections.singletonList("*");

		private List<HttpMethod> allowedMethods = Arrays.asList(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT,
				HttpMethod.DELETE, HttpMethod.OPTIONS, HttpMethod.PATCH, HttpMethod.TRACE);

		private List<String> allowedHeaders = Collections.singletonList("*");

		private Boolean allowCredentials = true;

		/*
		 *
		 * public String getPath() { return path; }
		 *
		 * public void setPath(String path) { this.path = path; }
		 *
		 * public List<String> getAllowedOrigins() { return allowedOrigins; }
		 *
		 * public void setAllowedOrigins(List<String> allowedOrigins) {
		 * this.allowedOrigins = allowedOrigins; }
		 *
		 * public List<HttpMethod> getAllowedMethods() { return allowedMethods; }
		 *
		 * public void setAllowedMethods(List<HttpMethod> allowedMethods) {
		 * this.allowedMethods = allowedMethods; }
		 *
		 * public List<String> getAllowedHeaders() { return allowedHeaders; }
		 *
		 * public void setAllowedHeaders(List<String> allowedHeaders) {
		 * this.allowedHeaders = allowedHeaders; }
		 *
		 * public Boolean getAllowCredentials() { return allowCredentials; }
		 *
		 * public void setAllowCredentials(Boolean allowCredentials) {
		 * this.allowCredentials = allowCredentials; }
		 */

	}

	/**
	 *
	 */
	@Data
	public static class IgnorePath {

		private String path;

		private HttpMethod method;

		// public String getPath() {
		// return path;
		// }
		//
		// public void setPath(String path) {
		// this.path = path;
		// }
		//
		// public HttpMethod getMethod() {
		// return method;
		// }
		//
		// public void setMethod(HttpMethod method) {
		// this.method = method;
		// }

	}

}
