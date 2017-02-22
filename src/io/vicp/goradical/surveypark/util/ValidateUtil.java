package io.vicp.goradical.surveypark.util;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.struts2.UserAware;
import io.vicp.goradical.surveypark.struts2.action.BaseAction;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

/**
 * 校验工具类
 */
public class ValidateUtil {
	/**
	 * 判断字符串有效性
	 * @param src
	 * @return
	 */
	public static boolean isValid(String src) {
		if (src == null || "".equals(src.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断集合的有效性
	 * @param coll
	 * @return
	 */
	public static boolean isValid(Collection coll) {
		if (coll == null || coll.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断数组是否有效
	 * @param arr
	 * @return
	 */
	public static boolean isValid(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否有权限
	 * @return
	 */
	public static boolean hasRight(String namespace, String actionName, HttpServletRequest request, BaseAction action) {
		if (!ValidateUtil.isValid(namespace) || "/".equals(namespace)) {
			namespace = "";
		}
		//将超链接参数部分过滤掉
		if (actionName.contains("?")) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url = namespace + "/" + actionName;
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		Map<String, Right> map = (Map<String, Right>) servletContext.getAttribute("all_rights_map");
		Right right = map.get(url);
		if (right == null || right.isCommon()) {
			return true;
		} else {
			User user = (User) session.getAttribute("user");
			//是否登录
			if (user == null) {
				return false;
			} else {
				//userAware处理
				if (action != null && action instanceof UserAware) {
					((UserAware) action).setUser(user);
				}
				if (user.isSuperAdmin()) {
					return true;
				} else {
					//有权限吗？
					if (user.hasRight(right)) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}
}
