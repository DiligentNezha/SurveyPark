package io.vicp.goradical.surveypark.model;

import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.model.security.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户类
 */
public class User extends BaseEntity{
	private static final long serialVersionUID = 1599528378439781507L;

	private Integer id;
	private String email;
	private String name;
	private String password;
	private String nickName;
	private Date regDate = new Date();
	//角色集合
	private Set<Role> roles = new HashSet<>();
	//权限总和
	private long[] rightSum;

	//是否是超级管理员
	private boolean superAdmin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	/**
	 * 计算用户权限总和
	 */
	public void calculateRightSum() {
		int pos;
		long code;
		for (Role role : roles) {
			//判断是否是超级管理员
			if ("-1".equals(role.getRoleValue())) {
				superAdmin = true;
				//释放资源
				roles = null;
				return;
			}
			for (Right right : role.getRights()) {
				pos = right.getRightPos();
				code = right.getRightCode();
				rightSum[pos] = rightSum[pos] | code;
			}
		}
		//释放资源
		roles = null;
	}

	//判断用户是否拥有指定权限
	public boolean hasRight(Right right) {
		int pos = right.getRightPos();
		long code = right.getRightCode();
		return !((rightSum[pos] & code) == 0);
	}
}
