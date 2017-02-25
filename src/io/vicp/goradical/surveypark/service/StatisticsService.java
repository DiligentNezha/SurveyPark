package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.statistics.QuestionStatisticsModel;

/**
 * 统计服务
 */
public interface StatisticsService {
	QuestionStatisticsModel statistics(Integer qid);
	String statistics(Integer qid, boolean isJson);
}
