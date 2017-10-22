package cn.hnhy.hyoa.admin.identity.dao;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 角色数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:48:01
 */
public interface RoleDao extends HibernateDao {
	/**
	 * 分页查询角色
	 * @param pageModel
	 * @return 角色集合
	 */
	List<Role> getRoleByPage(PageModel pageModel);
	/**
	 * 删除角色
	 * @param ids
	 */
	void deleteRole(String[] ids);

}
