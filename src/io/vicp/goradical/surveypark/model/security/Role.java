package io.vicp.goradical.surveypark.model.security;

import io.vicp.goradical.surveypark.model.BaseEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 */
public class Role extends BaseEntity{
	private static final long serialVersionUID = 629933036597773539L;

	private Integer id;
	private String roleName;
	private String roleValue;
	private String roleDesc;

	//权限集合
	private Set<Right> rights = new HashSet<Right>();

	public Set<Right> getRights() {
		return rights;
	}

	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
