package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.UserService;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	/**
	 * 验证登陆信息
	 * @param email
	 * @param password
	 * @return
	 */
	@Override
	public User validateLoginInfo(String email, String password) {
		String hql = "from User u where u.email = ? and u.password = ?";
		List<User> userList = this.findEntityByHQL(hql, email, password);
		return ValidateUtil.isValid(userList) ? userList.get(0) : null;
	}

	/**
	 * 判断email是否已被占用
	 * @param email
	 * @return
	 */
	@Override
	public boolean isRegisted(String email) {
		String hql = "from User u where u.email = ?";
		List<User> userList = findEntityByHQL(hql, email);
		return ValidateUtil.isValid(userList);
	}
}
