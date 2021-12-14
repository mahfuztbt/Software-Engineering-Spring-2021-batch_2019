package com.mitonal.edu.service;

import com.mitonal.edu.common.service.AbstractTypedService;
import com.mitonal.edu.dao.StudentDao;
import com.mitonal.edu.entity.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends AbstractTypedService<Student, Long> {

	public StudentService(StudentDao dao){
		this.dataContext=dao;
	}
}
