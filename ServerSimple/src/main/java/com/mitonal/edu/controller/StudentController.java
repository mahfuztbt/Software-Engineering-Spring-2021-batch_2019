package com.mitonal.edu.controller;


import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.common.controller.AbstractTypedController;

import com.mitonal.edu.dao.SelectionDao;
import com.mitonal.edu.dao.StudentDao;
import com.mitonal.edu.dao.TClassDao;
import com.mitonal.edu.entity.Selection;
import com.mitonal.edu.entity.Student;
import com.mitonal.edu.entity.TClass;
import com.mitonal.edu.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "Student Entity")
@RequestMapping(ApiPrefix.BASE_PREFIX+"/Student")
@RestController
public class StudentController extends AbstractTypedController<Student,Long> {

	@Resource
	private StudentDao studentDao;

	@Resource
	private TClassDao tClassDao;

	public StudentController(StudentService service){
		this.svcContext=service;
	}

	@GetMapping("/takeClass")
	@ApiOperation("学生选课")
	public String takeClass(@ApiParam(value = "学生ID", required = true) @RequestParam("studentid") Long stuid,
							 @ApiParam(value = "教学班ID", required = true) @RequestParam("clsid")Long clsid){

		var student=studentDao.getOne(stuid);
		var tClass=tClassDao.getOne(clsid);

		//capacity is not full
		if(tClass.getCapacity().equals(tClass.getSelections().size())){
			return "FULL";
		}


		for(Selection selection: student.getSelections()){
			if(selection.getTClass().equals(tClass)){
				return "AReady Took!";
			}
		}

		// time conflict?
		for(Selection selection: student.getSelections()){
			if(selection.getTClass().getDayTime().equals(tClass.getDayTime())){
				if(selection.getTClass().getDay().equals(tClass.getDay()))
					return "CONFLICT";
			}
		}


		student.takeClass(tClass);

		studentDao.save(student);

		return  "OK";
	}

	@GetMapping("/withDraw")
	public String withDraw( @RequestParam("studentid") Long stuid,
							@RequestParam("clsid")Long clsid){
		var student=studentDao.getOne(stuid);
		var tClass=tClassDao.getOne(clsid);
		student.withDraw(tClass);
		studentDao.save(student);
//
		return "WithDraw:"+tClass.getCourse().getName();
	}

}
