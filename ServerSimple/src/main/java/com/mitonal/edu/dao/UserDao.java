package com.mitonal.edu.dao;

import com.mitonal.edu.common.data.BaseDao;
import com.mitonal.edu.entity.Student;
import com.mitonal.edu.entity.User;

public interface UserDao extends BaseDao<User,Long> {
	public User findByName(String name);
}
