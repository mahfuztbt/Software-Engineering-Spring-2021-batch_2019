package com.mitonal.edu.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mitonal.edu.common.entities.AbstractDomainEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@DiscriminatorValue("2")
public class Teacher extends User {

	@ApiModelProperty("所授教学班")
	@OneToMany(mappedBy = "teacher",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"teacher"})
	private  List<TClass> clses;

	public void takeClass(TClass tClass){
		if (clses == null)
			clses=new ArrayList<>();
		clses.add(tClass);
	}
}
