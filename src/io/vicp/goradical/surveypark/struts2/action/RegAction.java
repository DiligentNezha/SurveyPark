package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.UserService;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import io.vicp.goradical.surveypark.util.DataUtil;

/**
 * 注册Action
 */
@Controller
@Scope("prototype")
public class RegAction extends BaseAction<User> {
	private String confirmPassword;

	@Autowired
	private UserService userService;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * 到达注册页面
	 * @return
	 */
	@SkipValidation
	public String toRegPage() {
		return "regPage";
	}

	/**
	 * 进行用户注册
	 * @return
	 */
	public String doReg() {
		//密码加密
		model.setPassword(DataUtil.md5(model.getPassword()));
		userService.saveEntity(model);
		return SUCCESS;
	}

	/**
	 * 进行校验
	 * @return
	 */
	@Override
	public void validate() {
		//1.非空校验
		if (!ValidateUtil.isValid(model.getEmail())) {
			addFieldError("email", "email是必填项！");
		}
		if (!ValidateUtil.isValid(model.getPassword())) {
			addFieldError("password", "password是必填项！");
		}
		if (!ValidateUtil.isValid(model.getNickName())) {
			addFieldError("nickName", "nickName是必填项！");
		}
		if (hasErrors()) {
			return;
		}
		//2.密码一致性校验
		if (!model.getPassword().equals(confirmPassword)) {
			addFieldError("password", "密码不一致！");
			return;
		}
		//3.email占用校验
		if(userService.isRegisted(model.getEmail())) {
			addFieldError("email", "email已占用！");
		}
	}
}
