package com.mitonal.edu.controller;


import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.common.controller.AbstractTypedController;
import com.mitonal.edu.entity.Selection;
import com.mitonal.edu.entity.Student;
import com.mitonal.edu.service.SelectionService;
import com.mitonal.edu.service.StudentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "选课实体接口")
@RequestMapping(ApiPrefix.BASE_PREFIX+"/Selection")
@RestController
public class SelectionController extends AbstractTypedController<Selection,Long> {

	public SelectionController(SelectionService service){
		this.svcContext=service;
	}


}
