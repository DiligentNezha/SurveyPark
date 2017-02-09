package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.Page;
import io.vicp.goradical.surveypark.model.Question;
import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.model.User;

import java.util.List;

/**
 * SurveyService
 */
public interface SurveyService {
	/**
	 * 查询调查列表
	 * @param user
	 * @return
	 */
	List<Survey> findMySurveys(User user);

	/**
	 * 新建调查
	 * @param user
	 * @return
	 */
	Survey newSurvey(User user);

	/**
	 * 按照sid查询Survey,同时携带所有的孩子
	 * @param sid
	 * @return
	 */
	Survey getSurveyWithChildren(Integer sid);

	/**
	 * 按照sid查询Survey
	 * @param sid
	 * @return
	 */
	Survey getSurvey(Integer sid);

	/**
	 * 更新调查
	 * @param model
	 */
	void updateSurvey(Survey model);

	/**
	 * 保存/更新页面
	 * @param model
	 */
	void saveOrUpdatePage(Page model);

	/**
	 * 按照id查询页面
	 * @param pid
	 * @return
	 */
	Page getPage(Integer pid);

	/**
	 * 保存/更新问题
	 * @param model
	 */
	void saveOrUpdateQuestion(Question model);

	/**
	 * 删问题，同时删除答案
	 * @param qid
	 */
	void deleteQuestion(Integer qid);

	/**
	 * 删除Page,同时删除问题，答案
	 * @param pid
	 */
	void deletePage(Integer pid);

	/**
	 * 删除survey,同时删除Page,问题，答案
	 * @param sid
	 */
	void deleteSurvey(Integer sid);

	/**
	 * 编辑问题
	 * @param qid
	 * @return
	 */
	Question getQuestion(Integer qid);

	/**
	 * 清除调查
	 * @param sid
	 */
	void clearAnswers(Integer sid);

	/**
	 * 切换调查状态
	 * @param sid
	 */
	void toggleStatus(Integer sid);

	/**
	 * 更新logo的路径
	 * @param sid
	 * @param s
	 */
	void updateLogoPhotoPath(Integer sid, String path);

	/**
	 * 查询调查集合，携带pages
	 * @param user
	 * @return
	 */
	List<Survey> getSurveyWithPages(User user);
}
