package com.mitonal.edu.sys.service;

import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.service.iUserQueryService;
import com.mitonal.edu.sys.dao.UserInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoService implements iUserQueryService {
	@Resource
	UserInfoDao uDao;

	@Override
	public DefaultUserDetails findUserByIdNotNull(Object id) {
		var us = uDao.getOne((Long) id);
		var user = DefaultUserDetails.builder()
			.id(us.getId().toString())
			.name(us.getName())
			.username(us.getUserName())
			.password(us.getPassword())
			.build();

		//
		return user;
	}

	@Override
	public DefaultUserDetails findUserByUsername(String name) {
		var us = uDao.findByUserName(name);
		var user = DefaultUserDetails.builder()
			.id(us.getId().toString())
			.name(us.getName())
			.username(us.getUserName())
			.password(us.getPassword())
			.build();

		//
		return user;
	}
}
