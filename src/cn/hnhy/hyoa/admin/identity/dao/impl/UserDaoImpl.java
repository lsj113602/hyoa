package cn.hnhy.hyoa.admin.identity.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.identity.dao.UserDao;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 用户数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:44:43
 */
public class UserDaoImpl extends HibernateDaoImpl implements UserDao {
	/**
	 * 根据userId(加密)查询用户
	 * @param userId 用户id
	 * @return 用户
	 */
	public User getUser(String userId){
		String hql = "select u from User u where MD5(u.userId) = ?";
		return this.findUniqueEntity(hql, userId);
	}
	/**
	 * 多条件分页查询查询用户
	 * @param user 查询条件
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<User> getUserByPage(User user, PageModel pageModel){
		StringBuilder hql = new StringBuilder();
		hql.append("select u from User u where 1=1 ");
		/** 定义List集合封装查询参数 */
		List<Object> params = new ArrayList<>();
		if (user != null){
			/** 姓名 */
			if (!StringUtils.isEmpty(user.getName())){
				hql.append(" and u.name like ?");
				params.add("%" + user.getName() + "%");
			}
			/** 手机号码 */
			if (!StringUtils.isEmpty(user.getPhone())){
				hql.append(" and u.phone like ?");
				params.add("%" + user.getPhone() + "%");
			}
			/** 部门 */
			if (user.getDept() != null && user.getDept().getId() != null
					&& user.getDept().getId() > 0){
				hql.append(" and u.dept.id = ?");
				params.add(user.getDept().getId());
			}	
		}
		hql.append(" order by u.createDate asc");
		return this.findByPage(hql.toString(), pageModel, params);
	}
	/**
	 * 查询用户姓名
	 * @param name 查询条件
	 * @return List集合
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUserByName(String name){
		String hql = "select name from User where name like ?";
		return (List<String>)this.find(hql, new Object[]{"%" + name + "%"});
	}
	/**
	 * 批量删除用户
	 * @param userIds
	 */
	public void deleteUser(String[] userIds){
		// DELETE FROM `oa_id_user` WHERE user_id IN(?,?,?)
		// DML风格
		StringBuilder hql = new StringBuilder();
		hql.append("delete from User where userId in(");
		for (int i = 0; i < userIds.length; i++){
			hql.append(i == 0 ? "?" : ",?");
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), userIds);
	}
	/**
	 * 批量审核用户
	 * @param userIds 审核的用户id
	 * @param status 状态码
	 */
	public void checkUser(String[] userIds, Short status){
		// UPDATE `oa_id_user` SET check_date = ?, checker = ?, STATUS = ? WHERE user_id IN(?,?,?)
		StringBuilder hql = new StringBuilder();
		hql.append("update User set checkDate = ?, checker = ?, status = ? where userId in(");
		List<Object> params = new ArrayList<>();
		params.add(new Date());
		params.add(AdminConstant.getSessionUser());
		params.add(status);
		for (int i = 0; i < userIds.length; i++){
			hql.append(i == 0 ? "?" : ",?");
			params.add(userIds[i]);
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), params.toArray());
	}
	/**
	 * 根据角色id分页查询用户(把这个角色中的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<User> getUserByPageAndRoleId(Long id, PageModel pageModel){
		/** 写HQL语句时关联的属性是一个集合，需要显示的写join语句 */
		String hql = "select u from User u inner join u.roles as r where r.id = ? order by u.createDate asc";
		List<Object> params = new ArrayList<>();
		params.add(id);
		return this.findByPage(hql, pageModel, params);
	}
	/**
	 * 根据角色id分页查询用户(把这个角色中没有绑定的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<User> getUserByPageNotRoleId(Long id, PageModel pageModel){
		StringBuilder hql = new StringBuilder();
		hql.append("select u from User u where u.userId not in")
		   .append("(select us.userId from User us inner join us.roles as r where r.id = ?)")
		   .append(" order by u.createDate asc");
		List<Object> params = new ArrayList<>();
		params.add(id);
		return this.findByPage(hql.toString(), pageModel, params);
	}
}