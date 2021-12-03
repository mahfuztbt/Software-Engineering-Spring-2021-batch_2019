package com.mitonal.edu.dao;


import com.mitonal.edu.common.data.BaseDao;
import com.mitonal.edu.entity.Course;


public interface CourseDao extends BaseDao<Course,Long> {
	public  Course findByName(String name);
}
