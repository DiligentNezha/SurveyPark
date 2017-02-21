package io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.service.RightService;
import io.vicp.goradical.surveypark.util.DataUtil;
import io.vicp.goradical.surveypark.util.StringUtil;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

	/**
	 * 按照url追加权限
	 *
	 * @param url
	 */
	@Override
	public void appendRightByURL(String url) {
		String hql = "select count(*) from Right r where r.rightUrl = ?";
		Long count = (Long) uniqueResult(hql, url);
		if (count == 0) {
			Right right = new Right();
			right.setRightUrl(url);
			saveOrUpdateRight(right);
		}
	}

	/**
	 * 批量更新权限
	 *
	 * @param allRights
	 */
	@Override
	public void batchUpdateRights(List<Right> allRights) {
		String hql = "update Right r set r.rightName = ?, r.common = ? where r.id = ?";
		if (ValidateUtil.isValid(allRights)) {
			for (Right r : allRights) {
				batchEntityByHQL(hql, r.getRightName(), r.isCommon(), r.getId());
			}
		}

	}

	/**
	 * 查询在指定范围内的权限
	 */
	public List<Right> findRightsInRange(Integer[] ids){
		if(ValidateUtil.isValid(ids)){
			String hql = "from Right r where r.id in ("+ StringUtil.arr2Str(ids)+")" ;
			return this.findEntityByHQL(hql);
		}
		return null ;
	}

	/**
	 * 查询不在指定范围内的权限
	 */
	public List<Right> findRightsNotInRange(Set<Right> rights){
		if(!ValidateUtil.isValid(rights)){
			return this.findAllEntities();
		}
		else{
			String hql = "from Right r where r.id not in("+ DataUtil.extractRightIds(rights)+")" ;
			return this.findEntityByHQL(hql);
		}
	}

	/**
	 * 查询最大权限位
	 */
	public int getMaxRightPos(){
		String hql = "select max(r.rightPos) from Right r" ;
		Integer pos = (Integer) this.uniqueResult(hql);
		return pos == null ? 0 : pos ;
	}
}
