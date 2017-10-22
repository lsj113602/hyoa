package cn.hnhy.hyoa.admin.identity.dao;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 用户数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:48:17
 */
public interface UserDao extends HibernateDao {
	/**
	 * 根据userId(加密)查询用户
	 * @param userId 用户id
	 * @return 用户
	 */
	User getUser(String userId);
	/**
	 * 多条件分页查询查询用户
	 * @param user 查询条件
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	List<User> getUserByPage(User user, PageModel pageModel);
	/**
	 * 查询用户姓名
	 * @param name 查询条件
	 * @return List集合
	 */
	List<String> getUserByName(String name);
	/**
	 * 批量删除用户
	 * @param userIds
	 */
	void deleteUser(String[] userIds);
	/**
	 * 批量审核用户
	 * @param userIds 审核的用户id
	 * @param status 状态码
	 */
	void checkUser(String[] userIds, Short status);
	/**
	 * 根据角色id分页查询用户(把这个角色中的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	List<User> getUserByPageAndRoleId(Long id, PageModel pageModel);
	/**
	 * 根据角色id分页查询用户(把这个角色中没有绑定的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	List<User> getUserByPageNotRoleId(Long id, PageModel pageModel);

}
