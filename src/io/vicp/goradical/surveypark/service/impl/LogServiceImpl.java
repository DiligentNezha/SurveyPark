package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.Log;
import io.vicp.goradical.surveypark.service.LogService;
import org.springframework.stereotype.Service;

@Service(value = "logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {

	/**
	 * 通过表名生成日志表
	 *
	 * @param tableName
	 */
	@Override
	public void createLogTable(String tableName) {
		String sql = "create table if not exists " + tableName + " like t_logs";
		executeSQL(sql);
	}
}
