package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.dao.BaseDao;
import io.vicp.goradical.surveypark.model.*;
import io.vicp.goradical.surveypark.service.SurveyService;
import io.vicp.goradical.surveypark.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

	/**
	 * 更新logo的路径
	 *
	 * @param sid
	 * @param path
	 */
	@Override
	public void updateLogoPhotoPath(Integer sid, String path) {
		String hql = "update Survey s set s.logoPhotoPath = ? where s.id = ?";
		surveyDao.batchEntityByHQL(hql, path, sid);
	}

	/**
	 * 查询调查集合，携带pages
	 *
	 * @param user
	 * @return
	 */
	@Override
	public List<Survey> getSurveyWithPages(User user) {
		String hql = "from Survey s where s.user.id = ?";
		List<Survey> list = surveyDao.findEntityByHQL(hql, user.getId());
		for (Survey survey : list) {
			survey.getPages().size();
		}
		return list;
	}

	/**
	 * 进行页面移动/复制
	 *
	 * @param srcPid
	 * @param targPid
	 * @param pos
	 */
	@Override
	public void moveOrCopyePage(Integer srcPid, Integer targPid, int pos) {
		Page srcPage = getPage(srcPid);
		Survey srcSurvey = srcPage.getSurvey();
		Page targPage = getPage(targPid);
		Survey targSurvey = targPage.getSurvey();
		//判断移动还是复制
		if (srcSurvey.getId().equals(targSurvey.getId())) {
			setOrderno(srcPage, targPage, pos);
		} else {
			//强行初始化问题集合，否则深度复制的页面对象没有问题集合
			srcPage.getQuestions().size();
			//深度复制
			Page copyPage = (Page) DataUtil.deeplyCopy(srcPage);
			//设置页面和目标页面关联
			copyPage.setSurvey(targSurvey);
			//保存页面
			pageDao.saveEntity(copyPage);
			for (Question question : copyPage.getQuestions()) {
				questionDao.saveEntity(question);
			}
			setOrderno(copyPage, targPage, pos);
		}
	}

	/**
	 * 查询所有可用调查
	 *
	 * @return
	 */
	@Override
	public List<Survey> findAllAvaliableSurveys() {
		String hql = "from Survey s where s.closed = ?";
		return surveyDao.findEntityByHQL(hql, false);
	}

	/**
	 * 查询调查的首页
	 *
	 * @param sid
	 * @return
	 */
	@Override
	public Page getFirstPage(Integer sid) {
		String hql = "from Page p where p.survey.id = ? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, sid);
		Page page = list.get(0);
		page.getQuestions().size();
		page.getSurvey().getTitle();
		return page;
	}

	/**
	 * 获得上一页
	 *
	 * @param currPid
	 * @return
	 */
	@Override
	public Page getPrePage(Integer currPid) {
		Page page = getPage(currPid);
		page = getPrePage(page);
		page.getQuestions().size();
		return page;
	}

	/**
	 * 获得下一页
	 *
	 * @param currPid
	 * @return
	 */
	@Override
	public Page getNextPage(Integer currPid) {
		Page page = getPage(currPid);
		page = getNextPage(page);
		page.getQuestions().size();
		return page;
	}

	/**
	 * 批量保存答案
	 *
	 * @param answers
	 */
	@Override
	public void saveAnswers(List<Answer> answers) {
		Date date = new Date();
		String uuid = UUID.randomUUID().toString();
		for (Answer answer : answers) {
			answer.setUuid(uuid);
			answer.setAnswerTime(date);
			answerDao.saveEntity(answer);
		}
	}

	/**
	 * 设置页序
	 * @param srcPage
	 * @param targPage
	 * @param pos
	 */
	private void setOrderno(Page srcPage, Page targPage, int pos) {
		//之前
		if (pos == 0) {
			//判断目标页是否是首页
			if (isFirstPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() - 0.01f);
			} else {
				//取得目标页的前一页
				Page prePage = getPrePage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + prePage.getOrderno()) / 2);
			}
		} else {
			//判断目标页是否是尾页
			if (isLastPage(targPage)) {
				srcPage.setOrderno(targPage.getOrderno() + 0.01f);
			} else {
				//取得目标页的下一页
				Page nextPage = getNextPage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + nextPage.getOrderno()) / 2);
			}
		}
	}

	/**
	 * 查村指定页面的下一页
	 * @param targPage
	 * @return
	 */
	private Page getNextPage(Page targPage) {
		String hql = "from Page p where p.survey.id = ? and p.orderno > ? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return list.get(0);
	}

	/**
	 * 查询指定页面的上一页
	 * @param targPage
	 * @return
	 */
	private Page getPrePage(Page targPage) {
		String hql = "from Page p where p.survey.id = ? and p.orderno < ? order by p.orderno desc";
		List<Page> list = pageDao.findEntityByHQL(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return list.get(0);
	}

	/**
	 * 判断制定页面是否是尾页
	 * @param targPage
	 * @return
	 */
	private boolean isLastPage(Page targPage) {
		String hql = "select count(*) from Page p where p.survey.id = ? and p.orderno > ?";
		long count = (long) pageDao.uniqueResult(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return count == 0;
	}

	/**
	 * 判断制定页面是否是首页
	 * @param targPage
	 * @return
	 */
	private boolean isFirstPage(Page targPage) {
		String hql = "select count(*) from Page p where p.survey.id = ? and p.orderno < ?";
		long count = (long) pageDao.uniqueResult(hql, targPage.getSurvey().getId(), targPage.getOrderno());
		return count == 0;
	}
}
