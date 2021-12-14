package com.mitonal.edu.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mitonal.edu.common.entities.AbstractDomainEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
public class Selection extends AbstractDomainEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("物理主键")
	private Long id;

	@Column
	@ApiModelProperty("分数")
	private Double score;

	@ManyToOne(fetch = FetchType.EAGER)
	@ApiModelProperty("选课的学生")
	@JsonIgnoreProperties("selections")
	private Student student;

	@ManyToOne(fetch = FetchType.EAGER)
	@ApiModelProperty("教学班")
	@JsonIgnoreProperties("selections")
	private TClass tClass;

}
