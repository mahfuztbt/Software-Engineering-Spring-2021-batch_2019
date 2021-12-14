package com.mitonal.edu.dao;

import com.mitonal.edu.common.data.BaseDao;
import com.mitonal.edu.entity.Student;

public interface StudentDao extends BaseDao<Student,Long> {
	public  Student  findByName(String name);
}
