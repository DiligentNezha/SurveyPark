package io.vicp.goradical.surveypark.listener;

import io.vicp.goradical.surveypark.service.LogService;
import io.vicp.goradical.surveypark.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化权限监听器，在spring初始化完成后立即调用
 */
@Component
public class IniLogTablesListener implements ApplicationListener {

	@Autowired
	private LogService logService;

	@Override
	public void onApplicationEvent(ApplicationEvent ae) {
		//上下文刷新事件
		if (ae instanceof ContextRefreshedEvent) {
			String tableName = LogUtils.generateLogTableName(0);
			logService.createLogTable(tableName);
			tableName = LogUtils.generateLogTableName(1);
			logService.createLogTable(tableName);
			tableName = LogUtils.generateLogTableName(2);
			logService.createLogTable(tableName);
		}
	}

}
