package com.mitonal.edu.controller;


import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.common.controller.AbstractTypedController;
import com.mitonal.edu.dao.StudentDao;
import com.mitonal.edu.dao.TClassDao;
import com.mitonal.edu.dao.TeacherDao;
import com.mitonal.edu.entity.Selection;
import com.mitonal.edu.entity.TClass;
import com.mitonal.edu.entity.Teacher;
import com.mitonal.edu.service.TClassService;
import com.mitonal.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "教师接口")
@RequestMapping(ApiPrefix.BASE_PREFIX+"/Teacher")
@RestController
public class TeacherController extends AbstractTypedController<Teacher,Long> {

	@Resource
	private TClassDao tClassDao;

	@Resource
	private TeacherDao teacherDao;

	public TeacherController(TeacherService service){
		this.svcContext=service;
	}


	@GetMapping("/take")
	@ApiOperation("授课")
	public Teacher take(@ApiParam(value = "教师ID", required = true) @RequestParam("tid") Long tid,
							 @ApiParam(value = "教学班ID", required = true) @RequestParam("clsid")Long clsid) {
		var teacher=teacherDao.getOne(tid);
		var tClass=tClassDao.getOne(clsid);
		teacher.takeClass(tClass);
		teacherDao.save(teacher);
		return  teacher;
	}


}
