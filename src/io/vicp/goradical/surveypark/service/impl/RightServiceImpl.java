package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.service.RightService;
import org.springframework.stereotype.Service;

@Service(value = "rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements RightService {

	/**
	 * 保存/更新权限
	 *
	 * @param right
	 */
	@Override
	public void saveOrUpdateRight(Right right) {
		//插入操作
		int pos;
		long code;
		if (right.getId() == null) {
			//性能太低
			/*String hql = "from Right r order by r.rightPos desc, r.rightCode desc";
			List<Right> rights = findEntityByHQL(hql);
			if (!ValidateUtil.isValid(rights)) {
				pos = 0;
				code = 1L;
			} else {
				//得到最上边的right
				Right top = rights.get(0);
				int topPos = top.getRightPos();
				long topCode = top.getRightCode();
				//判断权限码是否达到最大值
				if (topCode >= (1L << 60)) {
					pos = topPos + 1;
					code = 1;
				} else {
					pos = topPos;
					code = topCode << 1;
				}
			}*/
			String hql = "select max(r.rightPos), max(r.rightCode) from Right r where r.rightPos = (select max (rr.rightPos) from Right rr)";
			Object[] arr = (Object[]) uniqueResult(hql);
			Integer topPos = (Integer) arr[0];
			Long topCode = (Long) arr[1];
			//没有权限时
			if (topPos == null) {
				pos = 0;
				code = 1L;
			} else {
				//权限码是否达到最大值
				if (topCode >= (1L << 60)) {
					pos = topPos + 1;
					code = 1L;
				} else {
					pos = topPos;
					code = topCode << 1;
				}
			}
			right.setRightPos(pos);
			right.setRightCode(code);
		}
		saveOrUpdateEntity(right);
	}
}
