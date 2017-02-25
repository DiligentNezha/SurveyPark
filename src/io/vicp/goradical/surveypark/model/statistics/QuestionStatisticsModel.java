package io.vicp.goradical.surveypark.model.statistics;

import io.vicp.goradical.surveypark.model.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 问题统计模型
 */
public class QuestionStatisticsModel implements Serializable{

	private static final long serialVersionUID = -5241001647538203833L;

	private Question question;
	/**
	 * 回答该问题的人数
	 */
	private int count;

	private List<OptionStatisticsModel> osms = new ArrayList<>();

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OptionStatisticsModel> getOsms() {
		return osms;
	}

	public void setOsms(List<OptionStatisticsModel> osms) {
		this.osms = osms;
	}
}
