package com.mitonal.edu.common.config;

public class ApiPrefix {

	// #region 前缀

	public static final String BASE_PREFIX = "/api/v1";

	public static final String SYS_PREFIX = "/sys/api/v1";

	public static final String Auth_API = SYS_PREFIX + "/auth-token";

	public static final String Oauth_PREFIX = "/oauth/api/";

	//
	public static final String PLATFORM_PREFIX = BASE_PREFIX + "/platform";

	public static final String BUSINESS_PREFIX = BASE_PREFIX + "/business";

	public static final String Test_PREFIX = BASE_PREFIX + "/test";

	public static final String EDU_PREFIX = BASE_PREFIX + "/eduaction";

	public static final String Sketch_PREFIX = BASE_PREFIX + "/sketch";
	// #endregion

	// #region 后缀

	/**
	 * 认证类api 基本后缀
	 */
	public static final String AUTH_Public_POSTFIX = "/public/";

	// #endregion

}
