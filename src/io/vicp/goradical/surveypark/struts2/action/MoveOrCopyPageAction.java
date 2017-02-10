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
	//原页面id
	private Integer srcPid;
	//目标页面id
	private Integer targPid;
	//位置
	private int pos;
	//目标调查id
	private Integer sid;

	public Integer getTargPid() {
		return targPid;
	}

	public void setTargPid(Integer targPid) {
		this.targPid = targPid;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

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

	/**
	 * 进行页面移动/复制
	 * @return
	 */
	public String doMoveOrCopyPage() {
		surveyService.moveOrCopyePage(srcPid, targPid, pos);
		return "designSurveyAction";
	}

	//注入用户
	@Override
	public void setUser(User user) {
		this.user = user;
	}
}
