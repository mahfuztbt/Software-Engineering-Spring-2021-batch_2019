package com.mitonal.edu.service;

import com.mitonal.edu.common.service.AbstractTypedService;
import com.mitonal.edu.dao.UserDao;
import com.mitonal.edu.entity.User;

public class UserService extends AbstractTypedService<User, Long> {
	public UserService(UserDao dao){
		this.dataContext=dao;
	}
}
