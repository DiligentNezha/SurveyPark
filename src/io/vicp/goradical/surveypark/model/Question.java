package io.vicp.goradical.surveypark.model;

import io.vicp.goradical.surveypark.util.StringUtil;

import java.util.Arrays;

/**
 * 问题类
 */
public class Question {
	private static final String RN = "\r\n";

	private Integer id;
	//题型0-8
	private int questionType;
	private String title;
	//选项
	private String options;
	private String[] optionArr;
	//其他项
	private boolean other;
	//其他项样式
	private int otherStyle;
	//其他项下拉选项
	private String otherSelectOptions;
	private String[] otherSelectOptionArr;
	//矩阵式行标题集
	private String matrixRowTitles;
	private String[] matrixRowTitleArr;
	//矩阵式列标题集
	private String matrixColTitles;
	private String[] matrixColTitleArr;
	//矩阵式下拉选项集
	private String matrixSelectOptions;
	private String[] matrixSelectOptionArr;

	//建立从 Question 到 Page 之间多对一的关联关系
	private Page page;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return options;
	}

	/**
	 * 重写该方法完成字符串的拆分
	 * @param options
	 */
	public void setOptions(String options) {
		this.options = options;
		optionArr = StringUtil.str2Arr(options, RN);
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public int getOtherStyle() {
		return otherStyle;
	}

	public void setOtherStyle(int otherStyle) {
		this.otherStyle = otherStyle;
	}

	public String getOtherSelectOptions() {
		return otherSelectOptions;
	}

	public void setOtherSelectOptions(String otherSelectOptions) {
		this.otherSelectOptions = otherSelectOptions;
		otherSelectOptionArr = StringUtil.str2Arr(otherSelectOptions, RN);
	}

	public String getMatrixRowTitles() {
		return matrixRowTitles;
	}

	public void setMatrixRowTitles(String matrixRowTitles) {
		this.matrixRowTitles = matrixRowTitles;
		matrixRowTitleArr = StringUtil.str2Arr(matrixRowTitles, RN);
	}

	public String getMatrixColTitles() {
		return matrixColTitles;
	}

	public void setMatrixColTitles(String matrixColTitles) {
		this.matrixColTitles = matrixColTitles;
		matrixColTitleArr = StringUtil.str2Arr(matrixColTitles, RN);
	}

	public String getMatrixSelectOptions() {
		return matrixSelectOptions;
	}

	public void setMatrixSelectOptions(String matrixSelectOptions) {
		this.matrixSelectOptions = matrixSelectOptions;
		matrixSelectOptionArr = StringUtil.str2Arr(matrixSelectOptions, RN);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String[] getOptionArr() {
		return optionArr;
	}

	public void setOptionArr(String[] optionArr) {
		this.optionArr = optionArr;
	}

	public String[] getOtherSelectOptionArr() {
		return otherSelectOptionArr;
	}

	public void setOtherSelectOptionArr(String[] otherSelectOptionArr) {
		this.otherSelectOptionArr = otherSelectOptionArr;
	}

	public String[] getMatrixRowTitleArr() {
		return matrixRowTitleArr;
	}

	public void setMatrixRowTitleArr(String[] matrixRowTitleArr) {
		this.matrixRowTitleArr = matrixRowTitleArr;
	}

	public String[] getMatrixColTitleArr() {
		return matrixColTitleArr;
	}

	public void setMatrixColTitleArr(String[] matrixColTitleArr) {
		this.matrixColTitleArr = matrixColTitleArr;
	}

	public String[] getMatrixSelectOptionArr() {
		return matrixSelectOptionArr;
	}

	public void setMatrixSelectOptionArr(String[] matrixSelectOptionArr) {
		this.matrixSelectOptionArr = matrixSelectOptionArr;
	}

	@Override
	public String toString() {
		return "Question{" +
				"id=" + id +
				", questionType=" + questionType +
				", title='" + title + '\'' +
				", options='" + options + '\'' +
				", optionArr=" + Arrays.toString(optionArr) +
				", other=" + other +
				", otherStyle=" + otherStyle +
				", otherSelectOptions='" + otherSelectOptions + '\'' +
				", otherSelectOptionArr=" + Arrays.toString(otherSelectOptionArr) +
				", matrixRowTitles='" + matrixRowTitles + '\'' +
				", matrixRowTitleArr=" + Arrays.toString(matrixRowTitleArr) +
				", matrixColTitles='" + matrixColTitles + '\'' +
				", matrixColTitleArr=" + Arrays.toString(matrixColTitleArr) +
				", matrixSelectOptions='" + matrixSelectOptions + '\'' +
				", matrixSelectOptionArr=" + Arrays.toString(matrixSelectOptionArr) +
				", page=" + page +
				'}';
	}
}
