package io.vicp.goradical.surveypark.model;

import java.io.Serializable;

/**
 * 抽象的实体超类，专门用于继承
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 4443192900681947539L;

	public abstract Integer getId();

	public abstract void setId(Integer id);
}
