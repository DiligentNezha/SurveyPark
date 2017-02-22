package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.Log;
import io.vicp.goradical.surveypark.service.LogService;
import org.springframework.stereotype.Service;

@Service(value = "logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {

	@Override
	public void saveLog(Log log) {
		Log temp = new Log();
		temp.setOperator(log.getOperator());
		temp.setOperName(log.getOperName());
		temp.setOperParams(log.getOperParams());
		temp.setOperResult(log.getResultMsg());
		temp.setResultMsg(log.getResultMsg());
		temp.setOperTime(log.getOperTime());
		getCurrentSession().save(temp);
	}
}
