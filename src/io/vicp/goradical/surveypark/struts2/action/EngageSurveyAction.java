package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Page;
import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.service.SurveyService;
import io.vicp.goradical.surveypark.util.StringUtil;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参与调查Action
 */
@Controller
@Scope("prototype")
public class EngageSurveyAction extends BaseAction<Survey> implements ServletContextAware, SessionAware, ParameterAware {

	@Autowired
	private SurveyService surveyService;
	//当前调查的key
	private static final String CURRENT_SURVEY = "current_survey";
	//所有参数的map
	private static final String ALL_PARAMS_MAP = "all_params_map";

	private List<Survey> surveys;
	//接收ServletContext
	private ServletContext sc;

	private Integer sid;

	private Page currPage;

	private Integer currPid;

	//接收sessionMap
	private Map<String, Object> sessionMap;
	//接收所有参数的map
	private Map<String, String[]> paramsMap;

	public Integer getCurrPid() {
		return currPid;
	}

	public void setCurrPid(Integer currPid) {
		this.currPid = currPid;
	}

	public Page getCurrPage() {
		return currPage;
	}

	/**
	 * 注入sessionMap
	 *
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
	 *
	 * @return
	 */
	public String findAllAvailableSurveys() {
		surveys = surveyService.findAllAvaliableSurveys();
		return "engageSurveyListPage";
	}

	/**
	 * 获得URL的地址
	 *
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
	 *
	 * @return
	 */
	public String entry() {
		//查询首页
		currPage = surveyService.getFirstPage(sid);
		//存放survey到session
		sessionMap.put(CURRENT_SURVEY, currPage.getSurvey());
		//存放所有参数的答案到session中（用于保存答案和回显）
		sessionMap.put(ALL_PARAMS_MAP, new HashMap<Integer, Map<String, String[]>>());
		return "engageSurveyPage";
	}

	/**
	 * 处理参与调查
	 *
	 * @return
	 */
	public String doEngageSurvey() {
		String submitName = getSubmitName();
		if (submitName.endsWith("pre")) {
			//上一步
			mergeParamsIntoSession();
			currPage = surveyService.getPrePage(currPid);
			return "engageSurveyPage";
		} else if (submitName.endsWith("next")) {
			//下一步
			mergeParamsIntoSession();
			currPage =  surveyService.getNextPage(currPid);
			return "engageSurveyPage";
		} else if (submitName.endsWith("done")) {
			//完成
			mergeParamsIntoSession();
			//答案入库
			clearSessionData();
			return "engageSurveyAction";
		} else if (submitName.endsWith("exit")) {
			//退出
			clearSessionData();
			return "engageSurveyAction";
		}
		return null;
	}

	/**
	 * 清除session数据
	 */
	private void clearSessionData() {
		sessionMap.remove(CURRENT_SURVEY);
		sessionMap.remove(ALL_PARAMS_MAP);
	}

	/**
	 * 设置标记，用于答案的回显,应用于radio|checkbox|select的回显
	 * @param name
	 * @param value
	 * @param selTag
	 * @return
	 */
	public String setTag(String name, String value, String selTag) {
		Map<String, String[]> map = getAllParamsMap().get(currPage.getId());
		String[] values = map.get(name);
		if (StringUtil.contains(values, value)) {
			return selTag;
		}
		return "";
	}

	/**
	 * 设置标记，用于答案的回显，文本框的回显
	 * @param name
	 * @return
	 */
	public String setText(String name) {
		Map<String, String[]> map = getAllParamsMap().get(currPage.getId());
		String[] values = map.get(name);
		return "value='" + values[0] + "'";
	}

	/**
	 * 合并参数到session中
	 */
	private void mergeParamsIntoSession() {
		Map<Integer, Map<String, String[]>> allParamsMap = getAllParamsMap();
		allParamsMap.put(currPid, paramsMap);
	}

	/**
	 * 获取session中存放参数的map
	 * @return
	 */
	private Map<Integer,Map<String,String[]>> getAllParamsMap() {
		return (Map<Integer, Map<String, String[]>>) sessionMap.get(ALL_PARAMS_MAP);
	}

	/**
	 * 获得提交按钮的名称
	 *
	 * @return
	 */
	private String getSubmitName() {
		for (String key : paramsMap.keySet()) {
			if (key.startsWith("submit_")) {
				return key;
			}
		}
		return null;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.sc = servletContext;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}

	/**
	 * 注入提交的所有的参数的map
	 *
	 * @param map
	 */
	@Override
	public void setParameters(Map<String, String[]> map) {
		this.paramsMap = map;
	}
}
