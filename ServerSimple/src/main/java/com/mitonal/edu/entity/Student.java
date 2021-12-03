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
@DiscriminatorValue("1")
public class Student extends User {
	@ApiModelProperty("所选的课")
	@OneToMany(orphanRemoval=true,mappedBy = "student",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("student")
	private List<Selection> selections;

	public  void takeClass(TClass tClass){
		if (selections ==null)
			selections=new ArrayList<>();
		Selection selection=new Selection();
		selection.setStudent(this);
		selection.setTClass(tClass);
		selections.add(selection);
	}

	public void withDraw(TClass tClass){
		Selection s=null;
		for(Selection selection : selections){
			if (selection.getTClass().equals(tClass)){
				s=selection;
			}
		}
		selections.remove(s);
	}
}
