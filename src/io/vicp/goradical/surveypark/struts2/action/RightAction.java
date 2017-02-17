package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * RightAction
 */
@Controller
@Scope("prototype")
public class RightAction extends BaseAction<Right> {

	@Autowired
	private RightService rightService;
	private List<Right> allRights;

	private Integer rightId;
	/**
	 * 查询所有权限
	 * @return
	 */
	public String findAllRights() {
		allRights = rightService.findAllEntities();
		return "rightListPage";
	}

	/**
	 * 添加权限
	 * @return
	 */
	public String toAddRightPage() {
		return "addRightPage";
	}

	/**
	 * 保存/更新权限
	 * @return
	 */
	public String saveOrUpdateRight() {
		rightService.saveOrUpdateRight(model);
		return "findAllRightAction";
	}

	/**
	 * 编辑权限
	 * @return
	 */
	public String editRight() {
		model = rightService.getEntity(rightId);
		return "editRightPage";
	}

	/**
	 * 删除权限
	 * @return
	 */
	public String deleteRight() {
		Right right = new Right();
		right.setId(rightId);
		rightService.deleteEntity(right);
		return "findAllRightAction";
	}

	/**
	 * 批量更新权限
	 * @return
	 */
	public String batchUpdateRights() {
		rightService.batchUpdateRights(allRights);
		return "findAllRightAction";
	}

	public List<Right> getAllRights() {
		return allRights;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}

	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}
}
