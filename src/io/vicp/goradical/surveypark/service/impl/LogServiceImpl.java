package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.Log;
import io.vicp.goradical.surveypark.service.LogService;
import io.vicp.goradical.surveypark.util.LogUtils;
import io.vicp.goradical.surveypark.util.StringUtil;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

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

	/**
	 * 查询最近指定月份数的日志
	 *
	 * @param n
	 * @return
	 */
	@Override
	public List<Log> finNearestLogs(int n) {
		String tableName = LogUtils.generateLogTableName(0);
		//查询最近的日志表名称
		String sql = "select table_name from information_schema.tables where table_schema='surveypark' " +
				"and table_name like 't_logs_%' " +
				"and table_name <= '" + tableName + "' " +
				"order by table_name desc limit 0, " + n;
		List list = executeSQLQuery(null, sql);
		//查询最近若干月的日志
		String logSql = "";
		String logName;
		for (int i = 0; i < list.size(); i++) {
			logName = (String) list.get(i);
			if (i == (list.size()) - 1) {
				logSql = logSql + "select * from " + logName;
			} else {
				logSql = logSql + "select * from " + logName + " union ";
			}
		}
		return executeSQLQuery(Log.class, logSql);
	}

	/**
	 * 重写该方法，动态插入日志
	 */
	@Override
	public void saveEntity(Log log) {
		String sql = "insert into " + LogUtils.generateLogTableName(0) + " values (?, ?, ?, ?, ?, ?, ?)";
		UUIDHexGenerator uuid = new UUIDHexGenerator();
		executeSQL(sql, uuid.generate(null, null), log.getOperator(),
				StringUtil.getDescString(log.getOperName(), 255),
				StringUtil.getDescString(log.getOperParams(), 255),
				StringUtil.getDescString(log.getOperResult(), 255),
				StringUtil.getDescString(log.getResultMsg(), 255),
				log.getOperTime());
	}
}
