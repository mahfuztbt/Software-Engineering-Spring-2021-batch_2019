package com.mitonal.edu.common.service;

import com.mitonal.edu.common.entities.DefaultUserDetails;

/**
 * 用户查询接口
 */
public interface iUserQueryService<T> {

	/**
	 * @param id
	 * @return
	 */
	DefaultUserDetails findUserByIdNotNull(T id);

	/**
	 * @param name
	 * @return
	 */
	DefaultUserDetails findUserByUsername(String name);

}
