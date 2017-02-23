package io.vicp.goradical.surveypark.scheduler;

import io.vicp.goradical.surveypark.service.LogService;
import io.vicp.goradical.surveypark.util.LogUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 创建日志表的石英任务
 */
public class CreateLogTableTask extends QuartzJobBean{

	private LogService logService;

	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * 生成日志表
	 * @param jobExecutionContext
	 * @throws JobExecutionException
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		//下一月
		String tableName = LogUtils.generateLogTableName(1);
		logService.createLogTable(tableName);
		//下两月
		tableName = LogUtils.generateLogTableName(2);
		logService.createLogTable(tableName);
		//下三月
		tableName = LogUtils.generateLogTableName(3);
		logService.createLogTable(tableName);
	}
}
