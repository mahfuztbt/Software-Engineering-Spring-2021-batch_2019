package com.mitonal.edu.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author ruoyi
 */
@Component
@Data
@ConfigurationProperties(prefix = "spin.appinfo")
public class AppInfo {

	/** 项目名称 */
	private String name;

	/** 版本 */
	private String version;

	/** 版权年份 */
	private String copyrightYear;

	/** 实例演示开关 */
	private boolean demoEnabled;

	/** 上传路径 */
	private static String profile;

	/** 获取地址开关 */
	private static boolean addressEnabled;

}
