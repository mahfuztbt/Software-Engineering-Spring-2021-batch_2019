package com.mitonal.edu.sys.entity;

import com.mitonal.edu.common.entities.AbstractDomainEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ApiModel(value = "用户信息")
public class UserInfo extends AbstractDomainEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String userName;

	@Column
	private String password;

	@Column
	private String name;

}
