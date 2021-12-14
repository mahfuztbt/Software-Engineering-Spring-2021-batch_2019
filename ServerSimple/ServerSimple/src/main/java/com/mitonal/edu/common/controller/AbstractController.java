package com.mitonal.edu.common.controller;

import com.mitonal.edu.common.data.specification.AbstractQueryCondition;
import com.mitonal.edu.common.entities.DefaultUserDetails;
import com.mitonal.edu.common.security.service.SessionStateService;
import com.mitonal.edu.common.service.AbstractTypedService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抽象控制器
 */
public class AbstractController {

	/**
	 * session 状态相关服务
	 */
	@Resource
	SessionStateService sessionSvc;

	DefaultUserDetails _currUser;

	protected DefaultUserDetails getCurrentUser() {
		if (_currUser == null) {
			_currUser = sessionSvc.getCurrentLoginUser();
		}

		return _currUser;
	}

}
