package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.SurveyService;
import io.vicp.goradical.surveypark.struts2.UserAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * SurveyAction
 */
@Controller
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey> implements UserAware{

	@Autowired
	private SurveyService surveyService;

	//调查集合
	private List<Survey> mySurveys;

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	/**
	 * 查询我的调查
	 * @return
	 */
	public String mySurveys() {
		mySurveys = surveyService.findMySurveys(user);
		return "mySurveyListPage";
	}

	/**
	 * 新建调查
	 * @return
	 */
	public String newSurvey() {
		model = surveyService.newSurvey(user);
		return "designSurveyPage";
	}

	//接收sid
	private Integer sid;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	/**
	 * 设计调查
	 * @return
	 */
	public String designSurvey() {
		model = surveyService.getSurveyWithChildren(sid);
		return "designSurveyPage";
	}

	/**
	 * 编辑调查
	 * @return
	 */
	public String editSurvey() {
		model = surveyService.getSurvey(sid);
		return "editSurveyPage";
	}

	/**
	 * 更新调查
	 * @return
	 */
	public String updateSurvey() {
		sid = model.getId();
		model.setUser(user);
		surveyService.updateSurvey(model);
		return "designSurveyAction";
	}

	/**
	 * 删除Survey
	 * @return
	 */
	public String deleteSurvey() {
		surveyService.deleteSurvey(sid);
		return "findMySurveyAction";
	}

	/**
	 * 清除调查答案
	 * @return
	 */
	public String clearAnswers() {
		surveyService.clearAnswers(sid);
		return "findMySurveyAction";
	}

	/**
	 * 切换调查状态
	 * @return
	 */
	public String toggleStatus() {
		surveyService.toggleStatus(sid);
		return "findMySurveyAction";
	}

	//接收用户对象的
	private User user;

	/**
	 * 注入User对象
	 * @param user
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}
}
