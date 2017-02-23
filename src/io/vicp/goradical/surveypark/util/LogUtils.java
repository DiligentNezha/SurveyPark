package io.vicp.goradical.surveypark.util;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 日志工具类
 */
public class LogUtils {
	/**
	 * 生成日志表名称:t_logs_2013_9
	 * @return
	 * @param offset 月份偏移量
	 */
	public static String generateLogTableName(int offset) {
		Calendar c = Calendar.getInstance();
		//2013
		int year = c.get(Calendar.YEAR);
		//0-11
		int month = c.get(Calendar.MONTH) + 1 + offset;
		if (month > 12) {
			year++;
			month = month - 12;
		}
		if (month < 1) {
			year--;
			month = month + 12;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("00");
		return "t_logs_" + year + "_" + df.format(month);
	}
}
