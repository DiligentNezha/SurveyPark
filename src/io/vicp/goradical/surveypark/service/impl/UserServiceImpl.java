package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.model.security.Role;
import io.vicp.goradical.surveypark.service.RoleService;
import io.vicp.goradical.surveypark.service.UserService;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private RoleService roleService;

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
	 * 更新授权(只能更新角色设置)
	 *
	 * @param model
	 * @param ownRoleIds
	 */
	@Override
	public void updateAuthorize(User model, Integer[] ownRoleIds) {
		//查询新对象，不可以对原有对象操作
		User newUser = getEntity(model.getId());
		if (!ValidateUtil.isValid(ownRoleIds)) {
			newUser.getRoles().clear();
		} else {
			List<Role> roles = roleService.findRolesInRange(ownRoleIds);
			newUser.setRoles(new HashSet<>(roles));
		}
	}

	/**
	 * 清除用户授权
	 *
	 * @param userId
	 */
	@Override
	public void clearAuthorize(Integer userId) {
		getEntity(userId).getRoles().clear();
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
