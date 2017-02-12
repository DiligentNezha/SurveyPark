package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.Answer;
import io.vicp.goradical.surveypark.model.Question;
import io.vicp.goradical.surveypark.model.Survey;
import io.vicp.goradical.surveypark.service.SurveyService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收集调查
 */
@Controller
@Scope("prototype")
public class CollectionSurveyAction extends BaseAction<Survey> {

	@Autowired
	private SurveyService surveyService;

	private Integer sid;

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public InputStream getIs() {
		try {
			//存放qid --> index 映射
			Map<Integer, Integer> qidIndexMap = new HashMap<>();
			List<Question> list = surveyService.getQuestions(sid);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("收集调查");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell;
			Question q;
			for (int i = 0; i < list.size(); i++) {
				q = list.get(i);
				cell = row.createCell(i);
				cell.setCellValue(q.getTitle());
				sheet.setColumnWidth(i, 6000);
				qidIndexMap.put(q.getId(), i);
			}
			HSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);

			//输出answer
			List<Answer> answers = surveyService.getAnswers(sid);
			String oldUuid = "";
			String newUuid = "";
			int rowIndex = 0;
			for (Answer a : answers) {
				newUuid = a.getUuid();
				if (!newUuid.equals(oldUuid)) {
					rowIndex++;
					oldUuid = newUuid;
					sheet.createRow(rowIndex);
				}
				cell = row.createCell(qidIndexMap.get(a.getQuestionId()));
				cell.setCellValue(a.getAnswerIds());
				cell.setCellStyle(style);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wb.write(baos);
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
