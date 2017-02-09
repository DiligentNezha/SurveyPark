package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Page;
import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.SurveyService;
import io.vicp.goradical.surveypark.struts2.UserAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 移动/复制页面Action
 */
@Controller
@Scope("prototype")
public class MoveOrCopyPageAction extends BaseAction<Page> implements UserAware{

	@Autowired
	private SurveyService surveyService;

	private Integer srcPid;

	private List<Survey> mySurveys;
	private User user;

	public Integer getSrcPid() {
		return srcPid;
	}

	public void setSrcPid(Integer srcPid) {
		this.srcPid = srcPid;
	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	/**
	 * 到达移动/复制页面
	 * @return
	 */
	public String toSelectTargetPage() {
		mySurveys = surveyService.getSurveyWithPages(user);
		return "moveOrCopyPageListPage";
	}

	//注入用户
	@Override
	public void setUser(User user) {
		this.user = user;
	}
}
