package com.mitonal.edu.entity;

import com.mitonal.edu.common.entities.AbstractDomainEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 2)
@DiscriminatorValue("0")
public class User extends AbstractDomainEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("物理主键")
	private Long id;

	@Column
	@ApiModelProperty("用户名")
	private String name;

	@Column
	@ApiModelProperty("密码")
	private String pwd;

	@Column
	@ApiModelProperty("性别")
	private String sex;

}
