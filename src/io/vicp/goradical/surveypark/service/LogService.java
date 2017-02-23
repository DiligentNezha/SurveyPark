package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.Log;

/**
 * 日志Service
 */
public interface LogService extends BaseService<Log>{

	/**
	 * 通过表名生成日志表
	 * @param tableName
	 */
	void createLogTable(String tableName);
}
