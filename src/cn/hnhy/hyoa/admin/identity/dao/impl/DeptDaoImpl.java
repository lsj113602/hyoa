package cn.hnhy.hyoa.admin.identity.dao.impl;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.identity.dao.DeptDao;
import cn.hnhy.hyoa.admin.identity.entity.Dept;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 部门数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午7:55:58
 */
public class DeptDaoImpl extends HibernateDaoImpl implements DeptDao {

	/**
	 * 加载部门
	 */
	public List<Map<String, Object>> getDeptByIdAndName() {
		String hql = "select new map(id as id, name as name) from Dept";
		return this.find(hql);
	}

	/**
	 * 分页查询部门
	 * @param pageModel
	 * @return
	 */
	public List<Dept> getDeptByPage(PageModel pageModel){
		return this.findByPage("from Dept order by modifyDate desc", pageModel, null);
	}
	/**
	 * 批量删除部门
	 * @param ids
	 */
	public void deleteDept(String[] idsStr){
		StringBuilder hql = new StringBuilder();
		Long[] ids = new Long[idsStr.length];
		hql.append("delete from Dept where id in(");
		for (int i = 0; i < ids.length; i++){
			hql.append(i == 0 ? "?" : ",?");
			ids[i] = Long.parseLong(idsStr[i]);
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), ids);
	}
}
