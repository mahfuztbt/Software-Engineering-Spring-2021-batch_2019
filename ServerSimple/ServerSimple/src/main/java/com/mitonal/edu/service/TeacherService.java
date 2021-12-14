package com.mitonal.edu.service;

import com.mitonal.edu.common.service.AbstractTypedService;
import com.mitonal.edu.dao.TClassDao;
import com.mitonal.edu.dao.TeacherDao;
import com.mitonal.edu.entity.TClass;
import com.mitonal.edu.entity.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends AbstractTypedService<Teacher, Long> {

	public TeacherService(TeacherDao dao){
		this.dataContext=dao;
	}
}
