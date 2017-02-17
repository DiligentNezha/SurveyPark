package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.security.Role;

import java.util.List;
import java.util.Set;

/**
 * roleService
 */
public interface RoleService extends BaseService<Role>{
	/**
	 * 保存/更新角色
	 */
	void saveOrUpdateRole(Role model, Integer[] ownRightIds);

	/**
	 * 查询不在指定范围中的角色集合
	 */
	List<Role> findRolesNotInRange(Set<Role> roles);

	/**
	 * 查询在指定范围中的角色集合
	 */
	List<Role> findRolesInRange(Integer[] ids);
}
