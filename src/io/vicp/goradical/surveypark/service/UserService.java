package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.User;

/**
 * 用户Service
 */
public interface UserService extends BaseService<User>{

	/**
	 * 判断email是否已被占用
	 * @param email
	 * @return
	 */
	boolean isRegisted(String email);

	/**
	 * 验证登陆信息
	 * @param email
	 * @param password
	 * @return
	 */
	User validateLoginInfo(String email, String password);
}
