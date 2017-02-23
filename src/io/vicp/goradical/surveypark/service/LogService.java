package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.Log;

import java.util.List;

/**
 * 日志Service
 */
public interface LogService extends BaseService<Log>{

	/**
	 * 通过表名生成日志表
	 * @param tableName
	 */
	void createLogTable(String tableName);

	/**
	 * 查询最近指定月份数的日志
	 * @param n
	 * @return
	 */
	List<Log> finNearestLogs(int n);
}
