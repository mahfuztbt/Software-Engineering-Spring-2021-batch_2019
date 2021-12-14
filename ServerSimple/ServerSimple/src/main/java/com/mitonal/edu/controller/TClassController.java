package com.mitonal.edu.controller;


import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.common.controller.AbstractTypedController;
import com.mitonal.edu.dao.StudentDao;
import com.mitonal.edu.dao.TClassDao;
import com.mitonal.edu.entity.Selection;
import com.mitonal.edu.entity.Student;
import com.mitonal.edu.entity.TClass;
import com.mitonal.edu.service.SelectionService;
import com.mitonal.edu.service.TClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "Classes")
@RequestMapping(ApiPrefix.BASE_PREFIX+"/TClass")
@RestController
public class TClassController extends AbstractTypedController<TClass,Long> {

	@Resource
	private StudentDao studentDao;

	@Resource
	private TClassDao tClassDao;

	public TClassController(TClassService service){
		this.svcContext=service;
	}


	@GetMapping("/scoring")
	@ApiOperation("评分")
	public Selection scoring(@ApiParam(value = "学生ID", required = true) @RequestParam("studentid") Long stuid,
							 @ApiParam(value = "教学班ID", required = true) @RequestParam("clsid")Long clsid,
							 @ApiParam(value = "分数", required = true) @RequestParam("score") Double score) {
		var student=studentDao.getOne(stuid);
		var tClass=tClassDao.getOne(clsid);
		Selection selection=tClass.scoring(student,score);
		tClassDao.save(tClass);

		return  selection;
	}


}
