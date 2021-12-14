package com.mitonal.edu.common.security.entites;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlatformLoginCommand {

	@ApiModelProperty("单点登录token")
	private String token;

	@ApiModelProperty("账号")
	private String username;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("客户端Id，目前有BROWSER和APP可选，前者过期时间较短")
	private String clientId;

	/**
	 *
	 */
	@ApiModelProperty("用户域")
	private String realm;

}
