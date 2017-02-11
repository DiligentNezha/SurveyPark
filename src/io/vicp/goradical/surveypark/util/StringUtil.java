package io.vicp.goradical.surveypark.util;

/**
 * 字符串工具类
 */
public class StringUtil {
	/**
	 * 将字符串转换成数组
	 * @param str
	 * @param tag
	 * @return
	 */
	public static String[] str2Arr(String str, String tag) {
		if (ValidateUtil.isValid(str)) {
			return str.split(tag);
		}
		return null;
	}

	/**
	 * 判断在values中是否含有指定的value
	 * @param values
	 * @param value
	 * @return
	 */
	public static boolean contains(String[] values, String value) {
		if (ValidateUtil.isValid(values)) {
			for (String s : values) {
				if (s.equals(value)) {
					 return true;
				}
			}
		}
		return false;
	}
}
