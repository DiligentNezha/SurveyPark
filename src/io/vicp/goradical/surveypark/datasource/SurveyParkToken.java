package io.vicp.goradical.surveypark.datasource;

import io.vicp.goradical.surveypark.model.Survey;

/**
 * 令牌
 */
public class SurveyParkToken {
	private static ThreadLocal<SurveyParkToken> local = new ThreadLocal<>();

	private Survey survey;

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	/**
	 * 将指定的对象绑定到当前线程中
	 * @param token
	 */
	public static void bindToken(SurveyParkToken token) {
		local.set(token);
	}

	/**
	 * 解除当前线程绑定的令牌
	 */
	public static void unbindToken() {
		local.remove();
	}

	/**
	 * 从当前线程中获得绑定的令牌
	 * @return
	 */
	public static SurveyParkToken getCurrentToken() {
		return local.get();
	}
}
