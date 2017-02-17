package io.vicp.goradical.surveypark.service;

import io.vicp.goradical.surveypark.model.security.Right;

import java.util.List;

/**
 * rightService
 */
public interface RightService extends BaseService<Right>{

	/**
	 * 保存/更新权限
	 * @param model
	 */
	void saveOrUpdateRight(Right model);

	/**
	 * 按照url追加权限
	 * @param url
	 */
	void appendRightByURL(String url);

	/**
	 * 鼻梁更新权限
	 * @param allRights
	 */
	void batchUpdateRights(List<Right> allRights);
}
