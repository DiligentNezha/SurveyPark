package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.SurveyService;
import io.vicp.goradical.surveypark.struts2.UserAware;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.List;

/**
 * SurveyAction
 */
@Controller
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey> implements UserAware, ServletContextAware{

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

	//该方法只在updateSurvey()方法之前执行
	public void prepareUpdateSurvey() {
		inputPage = "/editSurvey.jsp";
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

	/**
	 * 到达增加logo的页面
	 * @return
	 */
	public String toAddLogoPage() {
		return "addLogoPage";
	}

	//上传文件
	private File logoPhoto;
	//文件名称
	private String logoPhotoFileName;

	public void prepareDoAddLogo() {
		inputPage = "/addLogo.jsp";
	}

	/**
	 * 实现logo上传
	 * @return
	 */
	public String doAddLogo() {
		if (ValidateUtil.isValid(logoPhotoFileName)) {
			//1.实现文件上传
			// /upload文件夹真是路径
			String dir = sc.getRealPath("/upload");
			//扩展名
			String ext = logoPhotoFileName.substring(logoPhotoFileName.lastIndexOf("."));
			long l = System.nanoTime();
			File newFile = new File(dir, l + ext);
			//文件另存为
			logoPhoto.renameTo(newFile);
			//2.更新路径
			surveyService.updateLogoPhotoPath(sid, "/upload/" + l + ext);
		}
		return "designSurveyAction";
	}

	/**
	 * 图片是否存在
	 * @return
	 */
	public boolean photoExists() {
		String path = model.getLogoPhotoPath();
		if (ValidateUtil.isValid(path)) {
			String realPath = sc.getRealPath(path);
			File file = new File(realPath);
			return file.exists();
		}
		return false;
	}

	//动态错误页指定
	private String inputPage;

	public String getInputPage() {
		return inputPage;
	}

	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
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

	public File getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhoto(File logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public String getLogoPhotoFileName() {
		return logoPhotoFileName;
	}

	public void setLogoPhotoFileName(String logoPhotoFileName) {
		this.logoPhotoFileName = logoPhotoFileName;
	}

	//接受servletContext对象
	private ServletContext sc;
	//注入servletContext对象
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.sc = servletContext;
	}
}
