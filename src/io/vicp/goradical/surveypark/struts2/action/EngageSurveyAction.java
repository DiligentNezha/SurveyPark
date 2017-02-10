package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Page;
import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.service.SurveyService;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 参与调查Action
 */
@Controller
@Scope("prototype")
public class EngageSurveyAction extends BaseAction<Survey> implements ServletContextAware, SessionAware{

	@Autowired
	private SurveyService surveyService;
	//当前调查的key
	private static final String CURRENT_SURVEY = "current_survey";

	private List<Survey> surveys;
	//接收ServletContext
	private ServletContext sc;

	private Integer sid;

	private Page currPage;

	//接收sessionMap
	private Map<String, Object> sessionMap;

	public Page getCurrPage() {
		return currPage;
	}

	/**
	 * 注入sessionMap
	 * @param currPage
	 */
	public void setCurrPage(Page currPage) {
		this.currPage = currPage;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	/**
	 * 查询所有可用的调查
	 * @return
	 */
	public String findAllAvailableSurveys() {
		surveys = surveyService.findAllAvaliableSurveys();
		return "engageSurveyListPage";
	}

	/**
	 * 获得URL的地址
	 * @param path
	 * @return
	 */
	public String getImageUrl(String path) {
		if (ValidateUtil.isValid(path)) {
			String realPath = sc.getRealPath(path);
			File file = new File(realPath);
			if (file.exists()) {
				return sc.getContextPath() + path;
			}
		}
		return sc.getContextPath() + "/question.bmp";
	}

	/**
	 * 首次进入参与调查
	 * @return
	 */
	public String entry() {
		//查询首页
		currPage = surveyService.getFirstPage(sid);
		//存放survey到session
		sessionMap.put(CURRENT_SURVEY, currPage.getSurvey());
		return "engageSurveyPage";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.sc = servletContext;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}
}
