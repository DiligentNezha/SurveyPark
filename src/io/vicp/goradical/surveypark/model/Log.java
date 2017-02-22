package io.vicp.goradical.surveypark.model;

import java.util.Date;

/**
 * Log
 */
public class Log extends BaseEntity{
	private static final long serialVersionUID = -1918859923631021537L;

	private Integer id;

	private String operator;
	private String operName;
	private String operParams;
	private String operResult;
	private String resultMsg;
	private Date operTime = new Date();

	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public void setId(Integer id) {

	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperParams() {
		return operParams;
	}

	public void setOperParams(String operParams) {
		this.operParams = operParams;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
}
