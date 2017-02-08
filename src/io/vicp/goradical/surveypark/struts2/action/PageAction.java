package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Page;
import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * PageAction
 */
@Controller
@Scope(value = "prototype")
public class PageAction extends BaseAction<Page> {

	@Autowired
	private SurveyService surveyService;

	private Integer sid;

	private Integer pid;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 到达增加page的页面
	 * @return
	 */
	public String toAddPage() {
		return "addPagePage";
	}

	/**
	 * 编辑页面
	 * @return
	 */
	public String editPage() {
		model = surveyService.getPage(pid);
		return "editPagePage";
	}

	/**
	 * 保存更新页面
	 * @return
	 */
	public String saveOrUpdatePage() {
		//维护关联关系
		Survey survey = new Survey();
		survey.setId(sid);
		model.setSurvey(survey);
		surveyService.saveOrUpdatePage(model);
		return "designSurveyAction";
	}

	public String deletePage() {
		surveyService.deletePage(pid);
		return "designSurveyAction";
	}
}
