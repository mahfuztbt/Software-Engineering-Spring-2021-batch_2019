package com.mitonal.edu.entity;

import com.mitonal.edu.common.entities.AbstractDomainEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Course extends AbstractDomainEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("物理主键")
	private Long id;

	@Column
	@ApiModelProperty("学生姓名")
	private String name;


	@Column
	@ApiModelProperty("学分")
	private Double credit;

}
