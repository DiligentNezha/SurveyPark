package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.dao.BaseDao;
import io.vicp.goradical.surveypark.model.*;
import io.vicp.goradical.surveypark.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SurveyService实现
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private BaseDao<Survey> surveyDao;

	@Autowired
	private BaseDao<Page> pageDao;

	@Autowired
	private BaseDao<Question> questionDao;

	@Autowired
	private BaseDao<Answer> answerDao;

	/**
	 * 查询调查集合
	 * @param user
	 * @return
	 */
	@Override
	public List<Survey> findMySurveys(User user) {
		String hql = "from Survey s where s.user.id = ?";
		return surveyDao.findEntityByHQL(hql, user.getId());
	}

	/**
	 * 新建调查
	 * @param user
	 * @return
	 */
	@Override
	public Survey newSurvey(User user) {
		Survey s = new Survey();
		Page p = new Page();
		//设置关联
		s.setUser(user);
		p.setSurvey(s);
		s.getPages().add(p);
		surveyDao.saveEntity(s);
		pageDao.saveEntity(p);
		return s;
	}

	/**
	 * 按照sid查询Survey,同时携带所有的孩子
	 *
	 * @param sid
	 * @return
	 */
	@Override
	public Survey getSurveyWithChildren(Integer sid) {
//		Survey survey = surveyDao.getEntity(sid);
		Survey survey = getSurvey(sid);
		//强行初始化pages集合和question集合
		for (Page page : survey.getPages()) {
			page.getQuestions().size();
		}
		return survey;
	}

	/**
	 * 按照sid查询Survey
	 * @param sid
	 * @return
	 */
	@Override
	public Survey getSurvey(Integer sid) {
		return surveyDao.getEntity(sid);
	}

	/**
	 * 更新调查
	 *
	 * @param model
	 */
	@Override
	public void updateSurvey(Survey model) {
		surveyDao.updateEntity(model);
	}

	/**
	 * 保存/更新页面
	 *
	 * @param model
	 */
	@Override
	public void saveOrUpdatePage(Page model) {
		pageDao.saveOrUpdateEntity(model);
	}

	/**
	 * 按照id查询页面
	 *
	 * @param pid
	 * @return
	 */
	@Override
	public Page getPage(Integer pid) {
		return pageDao.getEntity(pid);
	}

	/**
	 * 保存/更新问题
	 *
	 * @param model
	 */
	@Override
	public void saveOrUpdateQuestion(Question model) {
		questionDao.saveOrUpdateEntity(model);
	}

	/**
	 * 删问题，同时删除答案
	 *
	 * @param qid
	 */
	@Override
	public void deleteQuestion(Integer qid) {
		String hql = "delete from Answer a where a.questionId = ?";
		answerDao.batchEntityByHQL(hql, qid);
		hql = "delete from Question q where q.id = ?";
		questionDao.batchEntityByHQL(hql, qid);
	}

	/**
	 * 删除Page,同时删除问题，答案
	 *
	 * @param pid
	 */
	@Override
	public void deletePage(Integer pid) {
		//delete answer
		String hql = "delete from Answer a where a.questionId in (select p.id from Question p where p.page.id = ?)";
		answerDao.batchEntityByHQL(hql, pid);
		//delete questions
		hql = "delete from Question p where p.page.id = ?";
		questionDao.batchEntityByHQL(hql, pid);
		//delete page
		hql = "delete from Page p where p.id = ?";
		pageDao.batchEntityByHQL(hql, pid);
	}

	/**
	 * 删除survey,同时删除Page,问题，答案
	 *
	 * @param sid
	 */
	@Override
	public void deleteSurvey(Integer sid) {
		//delete answers
		String hql = "delete from Answer a where a.surveyId = ?";
		answerDao.batchEntityByHQL(hql, sid);
		//delete questions
		//hibernate 在写操作时，不允许两级以上的链接
		hql = "delete from Question q where q.page.id in (select p.id from Page p where p.survey.id = ?)";
		questionDao.batchEntityByHQL(hql, sid);
		//delete pages
		hql = "delete from Page p where p.survey.id = ?";
		pageDao.batchEntityByHQL(hql, sid);
		//delete survey
		hql = "delete from Survey s where s.id = ?";
		surveyDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * 编辑问题
	 *
	 * @param qid
	 * @return
	 */
	@Override
	public Question getQuestion(Integer qid) {
		return questionDao.getEntity(qid);
	}

	/**
	 * 清除调查
	 *
	 * @param sid
	 */
	@Override
	public void clearAnswers(Integer sid) {
		String hql = "delete from Answer a where a.surveyId = ?";
		answerDao.batchEntityByHQL(hql, sid);
	}

	/**
	 * 切换调查状态
	 *
	 * @param sid
	 */
	@Override
	public void toggleStatus(Integer sid) {
		Survey survey = getSurvey(sid);
		String hql = "update Survey s set s.closed = ? where s.id = ?";
		surveyDao.batchEntityByHQL(hql, !survey.isClosed(), sid);
	}
}
