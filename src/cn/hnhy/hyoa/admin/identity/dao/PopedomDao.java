package cn.hnhy.hyoa.admin.identity.dao;

import java.util.List;

import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 权限数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:47:35
 */
public interface PopedomDao extends HibernateDao {
	/**
	 * 查询该角色在这个模块中已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块代号
	 * @return 操作的集合
	 */
	List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode);
	/**
	 * 先根据模块代号与角色id删除以前绑定的权限
	 * @param id 角色id
	 * @param moduleCode 模块代号
	 */
	void delete(Long id, String moduleCode);
	/**
	 *  根据当前登录用户查询他所有能操作的模块代号
	 * @param userId
	 * @return List
	 */
	List<String> getModuleCodeByUserId(String userId);
	/**
	 * 根据用户id查询所有的角色，再根据角色查询所有的权限(12位code)
	 * @param userId
	 * @return List
	 */
	List<String> getOperaCodeByUserId(String userId);

}
