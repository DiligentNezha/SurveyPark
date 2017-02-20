package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.dao.BaseDao;
import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.model.security.Role;
import io.vicp.goradical.surveypark.service.RightService;
import io.vicp.goradical.surveypark.service.RoleService;
import io.vicp.goradical.surveypark.util.DataUtil;
import io.vicp.goradical.surveypark.util.StringUtil;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	@Resource
	private RightService rightService;

	@Autowired
	private BaseDao<Role> roleDao;


	/**
	 * 保存/更新角色
	 */
	public void saveOrUpdateRole(Role model, Integer[] ids) {
		//没有给角色授予任何权限
		if (!ValidateUtil.isValid(ids)) {
			model.getRights().clear();
		} else {
			List<Right> rights = rightService.findRightsInRange(ids);
			model.setRights(new HashSet<>(rights));
		}
		this.saveOrUpdateEntity(model);
	}


	/**
	 * 查询不在指定范围中的角色
	 */
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if (!ValidateUtil.isValid(roles)) {
			return this.findAllEntities();
		} else {
			String hql = "from Role r where r.id not in(" + DataUtil.extractRightIds(roles) + ")";
			return this.findEntityByHQL(hql);
		}
	}

	/**
	 * 查询在指定范围中的角色集合
	 */
	public List<Role> findRolesInRange(Integer[] ids) {
		if (ValidateUtil.isValid(ids)) {
			String hql = "from Role r where r.id in (" + StringUtil.arr2Str(ids) + ")";
			return this.findEntityByHQL(hql);
		}
		return null;
	}

}
