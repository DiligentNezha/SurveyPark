package io.vicp.goradical.surveypark.model.statistics;

import java.io.Serializable;

/**
 * 选项统计模型
 */
public class OptionStatisticsModel implements Serializable{

	private static final long serialVersionUID = -1203922235431342976L;
	/**
	 * 选项标签
	 */
	private String optionLabel;
	/**
	 * 选项索引
	 */
	private int optionIndex;

	private String matrixRowLabel;
	private int matrixRowIndex;

	private String matrixColLabel;
	private int matrixColIndex;

	private String matrixSelectLabel;
	private int matrixSelectIndex;

	/**
	 * 选项回答人数
	 */
	private int count;

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public int getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(int optionIndex) {
		this.optionIndex = optionIndex;
	}

	public String getMatrixRowLabel() {
		return matrixRowLabel;
	}

	public void setMatrixRowLabel(String matrixRowLabel) {
		this.matrixRowLabel = matrixRowLabel;
	}

	public int getMatrixRowIndex() {
		return matrixRowIndex;
	}

	public void setMatrixRowIndex(int matrixRowIndex) {
		this.matrixRowIndex = matrixRowIndex;
	}

	public String getMatrixColLabel() {
		return matrixColLabel;
	}

	public void setMatrixColLabel(String matrixColLabel) {
		this.matrixColLabel = matrixColLabel;
	}

	public int getMatrixColIndex() {
		return matrixColIndex;
	}

	public void setMatrixColIndex(int matrixColIndex) {
		this.matrixColIndex = matrixColIndex;
	}

	public String getMatrixSelectLabel() {
		return matrixSelectLabel;
	}

	public void setMatrixSelectLabel(String matrixSelectLabel) {
		this.matrixSelectLabel = matrixSelectLabel;
	}

	public int getMatrixSelectIndex() {
		return matrixSelectIndex;
	}

	public void setMatrixSelectIndex(int matrixSelectIndex) {
		this.matrixSelectIndex = matrixSelectIndex;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "OptionStatisticsModel{" +
				"optionLabel='" + optionLabel + '\'' +
				", optionIndex=" + optionIndex +
				", matrixRowLabel='" + matrixRowLabel + '\'' +
				", matrixRowIndex=" + matrixRowIndex +
				", matrixColLabel='" + matrixColLabel + '\'' +
				", matrixColIndex=" + matrixColIndex +
				", matrixSelectLabel='" + matrixSelectLabel + '\'' +
				", matrixSelectIndex=" + matrixSelectIndex +
				", count=" + count +
				'}';
	}
}
