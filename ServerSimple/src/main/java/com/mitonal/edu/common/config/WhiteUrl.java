package com.mitonal.edu.common.config;

import java.util.HashSet;
import java.util.Set;

/**
 * 不检查jwt token的api列表
 */
public class WhiteUrl {

	public static Set<String> urls = new HashSet<String>() {
		{
			add("/api/v1/platform/auth-token");
		}
	};

}
