package io.vicp.goradical.surveypark.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源路由器,用于分布式数据库
 */
public class SurveyParkDataSourceRouter extends AbstractRoutingDataSource {

	/**
	 * 检测当前的key
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		//得到当前的令牌
		SurveyParkToken currentToken = SurveyParkToken.getCurrentToken();
		if (currentToken != null) {
			Integer id = currentToken.getSurvey().getId();
			SurveyParkToken.unbindToken();
			return ((id % 2) == 0) ? "even" : "odd";
		}
		return null;
	}
}
