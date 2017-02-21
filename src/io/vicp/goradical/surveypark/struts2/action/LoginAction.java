package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.RightService;
import io.vicp.goradical.surveypark.service.UserService;
import io.vicp.goradical.surveypark.util.DataUtil;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * 登陆Action
 */
@Controller
@Scope(value = "prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{

	@Autowired
	private UserService userService;
	@Autowired
	private RightService rightService;

	/**
	 * 到达登陆页面
	 * @return
	 */
	public String toLoginPage() {
		return "loginPage";
	}

	public String doLogin() {
		return SUCCESS;
	}

	public void validateDoLogin() {
		User user = userService.validateLoginInfo(model.getEmail(), DataUtil.md5(model.getPassword()));
		if (user == null) {
			addActionError("email/password错误");
		} else {
			//初始化权限总和数组
			int maxPos = rightService.getMaxRightPos();
			user.setRightSum(new long[maxPos + 1]);
			//计算用户权限总和
			user.calculateRightSum();
			sessionMap.put("user", user);
		}
	}


	//接收Session的Map
	private Map<String, Object> sessionMap;
	/**
	 *
	 * @param map
	 */

	@Override
	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}
}
