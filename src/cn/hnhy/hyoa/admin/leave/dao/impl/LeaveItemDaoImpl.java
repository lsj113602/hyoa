package cn.hnhy.hyoa.admin.leave.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.leave.dao.LeaveItemDao;
import cn.hnhy.hyoa.admin.leave.entity.LeaveItem;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 假期明细访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午7:04:43
 */
public class LeaveItemDaoImpl extends HibernateDaoImpl implements LeaveItemDao {
	/**
	 * 分页查询当前登录用户的请假单
	 * @param leaveItem
	 * @param pageModel
	 * @return List
	 */
	public List<LeaveItem> getUserLeaveByPage(LeaveItem leaveItem, PageModel pageModel){
		StringBuilder hql = new StringBuilder();
		hql.append("select l from LeaveItem l where l.creater.userId = ?");
		/** 封装查询条件 */
		List<Object> params = new ArrayList<>();
		params.add(AdminConstant.getSessionUser().getUserId());
		/** 假期类型 */
		if (leaveItem != null){
			if (leaveItem.getLeaveType() != null && 
					!StringUtils.isEmpty(leaveItem.getLeaveType().getCode())){
				hql.append(" and l.leaveType.code = ?");
				params.add(leaveItem.getLeaveType().getCode());
			}
		}
		return this.findByPage(hql.toString(), pageModel, params);
	}
}
