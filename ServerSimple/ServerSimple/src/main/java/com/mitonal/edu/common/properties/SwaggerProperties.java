package com.mitonal.edu.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spin.swagger")
public class SwaggerProperties {

	private Boolean enable = true;

	private String urlPrefix = "";

}
