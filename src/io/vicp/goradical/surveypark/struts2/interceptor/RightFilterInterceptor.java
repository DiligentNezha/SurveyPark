package io.vicp.goradical.surveypark.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import io.vicp.goradical.surveypark.struts2.action.BaseAction;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.ServletActionContext;

/**
 * 登陆拦截器
 */
public class RightFilterInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		BaseAction action = (BaseAction) actionInvocation.getAction();
		ActionProxy proxy = actionInvocation.getProxy();
		String ns = proxy.getNamespace();
		String actionName = proxy.getActionName();
		if (ValidateUtil.hasRight(ns, actionName, ServletActionContext.getRequest(), action)) {
			return actionInvocation.invoke();
		}
		return "login";
	}
}
