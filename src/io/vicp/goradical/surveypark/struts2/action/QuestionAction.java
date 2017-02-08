package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Page;
import io.vicp.goradical.surveypark.model.Question;
import io.vicp.goradical.surveypark.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * QuestionAction
 */
@Controller
@Scope("prototype")
public class QuestionAction extends BaseAction<Question> {

	private Integer pid;
	private Integer sid;
	private Integer qid;

	@Autowired
	private SurveyService surveyService;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	/**
	 * 到达选择题型页面
	 * @return
	 */
	public String toSelectQuestionType() {
		return "selectQuestionTypePage";
	}

	/**
	 * 到达问题设计页面
	 * @return
	 */
	public String toDesignQuestionPage() {
		return "" + model.getQuestionType();
	}

	/**
	 * 保存/更新问题
	 * @return
	 */
	public String saveOrUpdateQuestion() {
		Page p = new Page();
		p.setId(pid);
		//维护关联关系
		model.setPage(p);
		surveyService.saveOrUpdateQuestion(model);
		return "designSurveyAction";
	}

	/**
	 * 删除问题
	 * @return
	 */
	public String deleteQuestion() {
		surveyService.deleteQuestion(qid);
		return "designSurveyAction";
	}

	/**
	 * 编辑问题
	 * @return
	 */
	public String editQuestion() {
		model = surveyService.getQuestion(qid);
		return "" + model.getQuestionType();
	}
}
