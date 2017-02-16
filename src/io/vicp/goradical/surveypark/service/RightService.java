package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.security.Right;

/**
 * rightService
 */
public interface RightService extends BaseService<Right>{

	/**
	 * 保存/更新权限
	 * @param model
	 */
	void saveOrUpdateRight(Right model);
}
