package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.dao.BaseDao;
import io.vicp.goradical.surveypark.model.Answer;
import io.vicp.goradical.surveypark.model.Question;
import io.vicp.goradical.surveypark.model.statistics.OptionStatisticsModel;
import io.vicp.goradical.surveypark.model.statistics.QuestionStatisticsModel;
import io.vicp.goradical.surveypark.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统计服务实现类
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
	@Autowired
	private BaseDao<Question> questionDao;
	@Autowired
	private BaseDao<Answer> answerDao;

	@Override
	public QuestionStatisticsModel statistics(Integer qid) {
		Question q = questionDao.getEntity(qid);
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		qsm.setQuestion(q);

		//统计回答问题的总人数
		String qhql = "select count(*) from Answer a where a.questionId = ?";
		Long qcount = (Long) answerDao.uniqueResult(qhql, qid);
		qsm.setCount(qcount.intValue());

		String ohql = "select count(*) from Answer a where a.questionId = ? and concat(',', a.answerIds, ',') like ?";
		Long ocount;
		//统计每个选项的情况
		int qt = q.getQuestionType();
		switch (qt) {
			//非矩阵式问题
			case 0:
			case 1:
			case 2:
			case 3:
				String[] arr = q.getOptionArr();
				OptionStatisticsModel osm;
				for (int i = 0; i < arr.length; i++) {
					osm = new OptionStatisticsModel();
					osm.setOptionLabel(arr[i]);
					osm.setOptionIndex(i);
					ocount = (Long) answerDao.uniqueResult(ohql, qid, "%," + i + ",%");
					osm.setCount(ocount.intValue());
					qsm.getOsms().add(osm);
				}
				//other
				if (q.isOther()) {
					osm = new OptionStatisticsModel();
					osm.setOptionLabel("其他");
					ocount = (Long) answerDao.uniqueResult(ohql, qid, "%other%");
					osm.setCount(ocount.intValue());
					qsm.getOsms().add(osm);
				}
				break;
			//矩阵式问题
			case 6:
			case 7:
			case 8:
				String[] rows = q.getMatrixRowTitleArr();
				String[] cols = q.getMatrixColTitleArr();
				String[] opts = q.getMatrixSelectOptionArr();
				for (int i = 0; i < rows.length; i++) {
					for (int j = 0; j < cols.length; j++) {
						if (qt != 8) {
							//matrix radio|checkbox
							osm = new OptionStatisticsModel();
							osm.setMatrixRowIndex(i);
							osm.setMatrixRowLabel(rows[i]);
							osm.setMatrixColIndex(j);
							osm.setMatrixColLabel(cols[j]);
							ocount = (Long) answerDao.uniqueResult(ohql, qid, "%," + i + "_" + j + ",%");
							osm.setCount(ocount.intValue());
							qsm.getOsms().add(osm);
						} else {
							for (int k = 0; k < opts.length; k++) {
								//matrix select
								osm = new OptionStatisticsModel();
								osm.setMatrixRowIndex(i);
								osm.setMatrixRowLabel(rows[i]);
								osm.setMatrixColIndex(j);
								osm.setMatrixColLabel(cols[j]);
								ocount = (Long) answerDao.uniqueResult(ohql, qid, "%," + i + "_" + j + "_" + k + ",%");
								osm.setCount(ocount.intValue());
								qsm.getOsms().add(osm);
							}
						}
					}
				}
				break;
			default:
		}

		return qsm;
	}

	@Override
	public String statistics(Integer qid, boolean isJson) {
		QuestionStatisticsModel statistics = statistics(qid);
		return statistics.toString();
	}
}
