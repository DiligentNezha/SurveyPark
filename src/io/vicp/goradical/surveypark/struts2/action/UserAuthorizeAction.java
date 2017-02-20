package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.model.security.Role;
import io.vicp.goradical.surveypark.service.RoleService;
import io.vicp.goradical.surveypark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 用户授权action
 */
@Controller
@Scope("prototype")
public class UserAuthorizeAction extends BaseAction<User> {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	private List<User> allUsers;
	private Integer userId;
	//用户没有的授权
	private List<Role> noOwnRoles;

	/**
	 * 查询所有用户
	 * @return
	 */
	public String findAllUsers() {
		allUsers = userService.findAllEntities();
		return "userAuthorizeListPage";
	}

	/**
	 * 修改授权
	 * @return
	 */
	public String editAuthorize() {
		model = userService.getEntity(userId);
		noOwnRoles = roleService.findRolesNotInRange(model.getRoles());
		return "editAuthorizePage";
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Role> getNoOwnRoles() {
		return noOwnRoles;
	}

	public void setNoOwnRoles(List<Role> noOwnRoles) {
		this.noOwnRoles = noOwnRoles;
	}
}
