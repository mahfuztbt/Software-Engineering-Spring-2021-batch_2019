package com.mitonal.edu.sys.dao;

import com.mitonal.edu.common.data.BaseDao;
import com.mitonal.edu.sys.entity.UserInfo;

/**
 * 用户
 */
public interface UserInfoDao extends BaseDao<UserInfo, Long> {

	/**
	 * 查询符合条件的用户
	 * @param userName ,password
	 * @return
	 */
	UserInfo findByUserNameAndPassword(String userName,String password);

	/**
	 * 查询符合条件的用户
	 * @param userName
	 * @return
	 */
	UserInfo findByUserName(String userName);

}
