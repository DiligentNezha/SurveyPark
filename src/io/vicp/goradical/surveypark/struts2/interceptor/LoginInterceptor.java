package io.vicp.goradical.surveypark.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.struts2.UserAware;
import io.vicp.goradical.surveypark.struts2.action.BaseAction;
import io.vicp.goradical.surveypark.struts2.action.FileUploadAction;
import io.vicp.goradical.surveypark.struts2.action.LoginAction;
import io.vicp.goradical.surveypark.struts2.action.RegAction;

/**
 * 登陆拦截器
 */
public class LoginInterceptor implements Interceptor {
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		BaseAction action = (BaseAction) actionInvocation.getAction();
		if (action instanceof LoginAction || action instanceof RegAction || action instanceof FileUploadAction) {
			return actionInvocation.invoke();
		} else {
			User user = (User) actionInvocation.getInvocationContext().getSession().get("user");
			if (user == null) {
				//去登陆
				return "login";
			} else {
				//放行
				if (action instanceof UserAware) {
					//注入user给action
					((UserAware) action).setUser(user);
				}
				return actionInvocation.invoke();
			}
		}
	}
}
