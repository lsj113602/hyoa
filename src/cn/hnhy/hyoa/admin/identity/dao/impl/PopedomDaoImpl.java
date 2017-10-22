package cn.hnhy.hyoa.admin.identity.dao.impl;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.dao.PopedomDao;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 权限数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:43:47
 */
@SuppressWarnings("unchecked")
public class PopedomDaoImpl extends HibernateDaoImpl implements PopedomDao {
	/**
	 * 查询该角色在这个模块中已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块代号
	 * @return 操作的集合
	 */
	public List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode){
		String hql = "select opera.code from Popedom where role.id = ? and module.code = ? order by opera.code asc";
		return (List<String>)this.find(hql, new Object[]{id, moduleCode});
	}
	/**
	 * 先根据模块代号与角色id删除以前绑定的权限
	 * @param id 角色id
	 * @param moduleCode 模块代号
	 */
	public void delete(Long id, String moduleCode){
		String hql = "delete from Popedom where role.id = ? and module.code = ?";
		this.bulkUpdate(hql, new Object[]{id, moduleCode});
	}
	/**
	 *  根据当前登录用户查询他所有能操作的模块代号
	 * @param userId
	 * @return List
	 */
	public List<String> getModuleCodeByUserId(String userId){
		StringBuilder hql = new StringBuilder();
		hql.append("select module.code from Popedom where role.id in");
		hql.append("(select r.id from Role as r inner join r.users as u where u.userId = ?)");
		hql.append(" group by module.code order by module.code asc");
		return (List<String>)this.find(hql.toString(), new Object[]{userId});
	}
	/**
	 * 根据用户id查询所有的角色，再根据角色查询所有的权限(12位code)
	 * @param userId
	 * @return List
	 */
	public List<String> getOperaCodeByUserId(String userId){
		StringBuilder hql = new StringBuilder();
		hql.append("select opera.code from Popedom where role.id in");
		hql.append("(select r.id from Role as r inner join r.users as u where u.userId = ?)");
		hql.append(" group by opera.code order by opera.code asc");
		return (List<String>)this.find(hql.toString(), new Object[]{userId});
	}
}
