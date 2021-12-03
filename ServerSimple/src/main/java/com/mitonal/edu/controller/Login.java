package com.mitonal.edu.controller;

import com.mitonal.edu.common.config.ApiPrefix;
import com.mitonal.edu.dao.UserDao;
import com.mitonal.edu.entity.User;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(ApiPrefix.BASE_PREFIX+"/login")
public class Login {

	@Resource
	private UserDao userDao;

	@PostMapping("/login")
	public User login (@RequestBody Map<String, String> user) {

		User userDB=userDao.findByName(user.get("name"));


		if (userDB == null){
			return null;
		}

		if (userDB.getPwd().equals(user.get("pwd"))){
			return  userDB;
		}

		return null;
	}
}
