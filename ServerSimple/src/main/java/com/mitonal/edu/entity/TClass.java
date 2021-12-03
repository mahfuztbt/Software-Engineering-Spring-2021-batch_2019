package com.mitonal.edu.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mitonal.edu.common.entities.AbstractDomainEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Setter
@Getter
public class TClass extends AbstractDomainEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("物理主键")
	private Long id;

	@Column
	@ApiModelProperty("教室")
	private String room;

	@Column
	@ApiModelProperty("Day WED MON FRI")
	private String day;

	@Column
	@ApiModelProperty("1-2 3-4 5-6 7-8")
	private String dayTime;


	@Column
	@ApiModelProperty("30 50 etc.")
	private Integer capacity;

	@ApiModelProperty("授课教师")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("clses")
	private Teacher teacher;

	@ApiModelProperty("课程")
	@ManyToOne(fetch = FetchType.LAZY)
	private  Course course;

	@ApiModelProperty("所选的学生")
	@OneToMany(mappedBy = "tClass",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tclass")
	private List<Selection> selections;

	public Selection scoring(Student student, Double score){
		for (Selection selection:selections) {
			if(selection.getStudent().equals(student)){
				selection.setScore(score);
				return selection;
			}
		}
		return  null;
	}

}
