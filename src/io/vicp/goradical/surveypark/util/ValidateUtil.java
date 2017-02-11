package io.vicp.goradical.surveypark.util;

import java.util.Collection;

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
}
