package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Question;
import io.vicp.goradical.surveypark.model.statistics.OptionStatisticsModel;
import io.vicp.goradical.surveypark.model.statistics.QuestionStatisticsModel;
import io.vicp.goradical.surveypark.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.text.DecimalFormat;

/**
 *
 */
@Controller
@Scope("prototype")
public class MatrixStatisticsAction extends BaseAction<Question> {

	@Autowired
	private StatisticsService statisticsService;
	//颜色数组 rgb
	private String[] colors = {
			"#ff0000",
			"#00ff00",
			"#0000ff",
			"#ffff00",
			"#ff00ff",
			"#00ffff"
	};

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	private Integer qid ;

	private QuestionStatisticsModel qsm ;

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public QuestionStatisticsModel getQsm() {
		return qsm;
	}

	public void setQsm(QuestionStatisticsModel qsm) {
		this.qsm = qsm;
	}

	public String execute() throws Exception {
		//先进行统计
		this.qsm = statisticsService.statistics(qid);
		return "" + qsm.getQuestion().getQuestionType();
	}

	/**
	 * 计算每个选项的统计结果
	 *
	 */
	public String getScale(int rowIndex,int colIndex){
		//问题回答人数
		int qcount = qsm.getCount();
		int ocount = 0 ;
		//选项回答人数
		for(OptionStatisticsModel osm : qsm.getOsms()){
			if(osm.getMatrixRowIndex() == rowIndex
					&& osm.getMatrixColIndex() == colIndex){
				ocount = osm.getCount();
				break ;
			}
		}
		float scale = 0 ;
		if(qcount != 0){
			scale = (float)ocount / qcount * 100;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#,###.00");
		return "" + ocount + "(" + df.format(scale) + "%)";
	}

	/**
	 * 计算每个选项的统计结果
	 *
	 */
	public String getScale(int rowIndex,int colIndex,int optIndex){
		//问题回答人数
		int qcount = qsm.getCount();
		int ocount = 0 ;
		//选项回答人数
		for(OptionStatisticsModel osm : qsm.getOsms()){
			if(osm.getMatrixRowIndex() == rowIndex
					&& osm.getMatrixColIndex() == colIndex
					&& osm.getMatrixSelectIndex() == optIndex){
				ocount = osm.getCount();
				break ;
			}
		}
		float scale = 0 ;
		if(qcount != 0){
			scale = (float)ocount / qcount * 100;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#,###.00");
		return "" + ocount + "(" + df.format(scale) + "%)";
	}

	/**
	 *
	 * 获得百分比的整数部分,作为选项的显示长度
	 */
	public int getPercent(int rowIndex,int colIndex,int optIndex){
		//问题回答人数
		int qcount = qsm.getCount();
		int ocount = 0 ;
		//选项回答人数
		for(OptionStatisticsModel osm : qsm.getOsms()){
			if(osm.getMatrixRowIndex() == rowIndex
					&& osm.getMatrixColIndex() == colIndex
					&& osm.getMatrixSelectIndex() == optIndex){
				ocount = osm.getCount();
				break ;
			}
		}
		float scale = 0 ;
		if(qcount != 0){
			scale = (float)ocount / qcount * 100;
		}
		return (int)scale ;
	}
}
