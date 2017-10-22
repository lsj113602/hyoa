package cn.hnhy.hyoa.admin.identity.dao.impl;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.dao.RoleDao;
import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;
/**
 * 权限数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:44:08
 */
public class RoleDaoImpl extends HibernateDaoImpl implements RoleDao {
	/**
	 * 分页查询角色
	 * @param pageModel
	 * @return 角色集合
	 */
	public List<Role> getRoleByPage(PageModel pageModel){
		return this.findByPage("from Role order by id asc", pageModel, null);
	}
	/**
	 * 删除角色
	 * @param ids
	 */
	public void deleteRole(String[] ids){
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Role where id in(");
		Long[] params = new Long[ids.length];
		for (int i = 0; i < ids.length; i++){
			hql.append(i == 0 ? "?" : ",?");
			params[i] = Long.valueOf(ids[i]);
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), params);
	}
}
