package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.model.security.Role;
import io.vicp.goradical.surveypark.service.RightService;
import io.vicp.goradical.surveypark.service.RoleService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private List<Role> allRoles ;

	//角色没有的权限集合
	private List<Right> noOwnRights ;

	private Integer roleId ;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Right> getNoOwnRights() {
		return noOwnRights;
	}

	public void setNoOwnRights(List<Right> noOwnRights) {
		this.noOwnRights = noOwnRights;
	}

	@Resource
	private RoleService roleService ;

	@Resource
	private RightService rightService ;

	//角色拥有的权限id数组
	private Integer[] ownRightIds ;

	public Integer[] getOwnRightIds() {
		return ownRightIds;
	}

	public void setOwnRightIds(Integer[] ownRightIds) {
		this.ownRightIds = ownRightIds;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}


	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}


	/**
	 * 查询所有角色
	 */
	public String findAllRoles(){
		this.allRoles = roleService.findAllEntities();
		return "roleListPage" ;
	}

	/**
	 * 添加角色
	 */
	public String toAddRolePage(){
		this.noOwnRights = rightService.findAllEntities();
		return "addRolePage" ;
	}


	/**
	 * 保存更新角色
	 */
	public String saveOrUpdateRole(){
		roleService.saveOrUpdateRole(model,ownRightIds);
		return "findAllRolesAction" ;
	}

	/**
	 * 编辑角色
	 */
	public String editRole(){
		this.model = roleService.getEntity(roleId);
		this.noOwnRights = rightService.findRightsNotInRange(model.getRights());
		return "editRolePage" ;
	}

	/**
	 * 删除角色
	 */
	public String deleteRole(){
		Role r = new Role();
		r.setId(roleId);
		roleService.deleteEntity(r);
		return "findAllRolesAction" ;
	}
}
