package io.vicp.goradical.surveypark.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.util.ValidateUtil;

/**
 * 登陆拦截器
 */
public class RightFilterInterceptor implements Interceptor {
	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionProxy proxy = actionInvocation.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if (!ValidateUtil.isValid(ns) || "/".equals(ns)) {
			ns = "";
		}
		String url = ns + "/" + actionName;
		//通过url查询权限对象
		Right right = null;
		if (right == null || right.isCommon()) {
			return actionInvocation.invoke();
		} else {
			User user = (User) actionInvocation.getInvocationContext().getSession().get("user");
			if (user == null) {
				return "login";
			} else {
				if (user.isSuperAdmin()) {
					return actionInvocation.invoke();
				} else {
					if (user.hasRight(right)) {
						return actionInvocation.invoke();
					} else {
						return "error_no_right";
					}
				}
			}
		}
	}
}
